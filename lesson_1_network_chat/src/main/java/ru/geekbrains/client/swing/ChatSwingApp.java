package ru.geekbrains.client.swing;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.client.SpringConfigClientSwing;

import javax.swing.*;

public class ChatSwingApp {

    public static void main(String[] args) {
        ApplicationContext contextClient = new AnnotationConfigApplicationContext(SpringConfigClientSwing.class);
        SwingUtilities.invokeLater(() -> contextClient.getBean("chatMainWindow", ChatMainWindow.class));
    }
}
