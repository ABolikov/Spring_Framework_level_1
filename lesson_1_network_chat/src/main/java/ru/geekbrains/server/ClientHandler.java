package ru.geekbrains.server;

import ru.geekbrains.client.TextMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Set;

import static ru.geekbrains.client.MessagePatterns.*;

public class ClientHandler {

    private final String login;
    private final Socket socket;
    private final DataInputStream inp;
    private final DataOutputStream out;
    private final Thread handleThread;
    private Server server;

    public ClientHandler(String login, Socket socket, Server server) throws IOException {
        this.login = login;
        this.socket = socket;
        this.inp = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        this.server = server;

        this.handleThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String text = inp.readUTF();
                    System.out.printf("Message from user %s: %s%n", login, text);

                    System.out.println("New message " + text);
                    TextMessage msg = parseTextMessageRegx(text, login);
                    if (msg != null) {
                        msg.swapUsers();
                        server.sendMessage(msg);
                    } else if (text.equals(DISCONNECT)) {
                        System.out.printf("User %s is disconnected%n", login);
                        server.unsubscribe(login);
                        return;
                    } else if (text.equals(USER_LIST_TAG)) {
                        System.out.printf("Sending user list to %s%n", login);
                        sendUserList(server.getUserList());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    server.unsubscribe(login);
                    break;
                }
            }
        });
        this.server = server;
        this.handleThread.start();
    }

    public String getLogin() {
        return login;
    }

    public void sendMessage(String userFrom, String msg) throws IOException {
        if (socket.isConnected()) {
            out.writeUTF(String.format(MESSAGE_SEND_PATTERN, userFrom, msg));
        }
    }

    public void sendConnectedMessage(String login) throws IOException {
        if (socket.isConnected()) {
            out.writeUTF(String.format(CONNECTED_SEND, login));
        }
    }

    public void sendDisconnectedMessage(String login) throws IOException {
        if (socket.isConnected()) {
            out.writeUTF(String.format(DISCONNECT_SEND, login));
        }
    }

    public void sendUserList(Set<String> users) throws IOException {
        if (socket.isConnected()) {
            out.writeUTF(String.format(USER_LIST_RESPONSE, String.join(" ", users)));
        }
    }
}
