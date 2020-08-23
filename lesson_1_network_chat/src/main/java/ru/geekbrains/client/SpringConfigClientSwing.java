package ru.geekbrains.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.geekbrains.client.swing.ChatMainWindow;
import sun.rmi.runtime.NewThreadAction;

@Configuration
@ComponentScan("package ru.geekbrains.client.swing")
public class SpringConfigClientSwing {

    @Bean("chatMainWindow")
    public ChatMainWindow chatMainWindow(Network network) {
        return new ChatMainWindow(network);
    }

    @Bean("network")
    public Network network(MessageReciever messageReciever) {
        return new Network("localhost",7777, messageReciever);
    }

}
