package ru.geekbrains.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RunServer {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_context.xml");
        Server server = context.getBean("server", Server.class);
        server.start(7777);
    }
}
