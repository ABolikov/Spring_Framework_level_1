package org.bolikov;

import org.bolikov.controller.ConsoleController;
import org.bolikov.controller.HibernateController;

public class Main {
    public static void main(String[] args) {
        new ConsoleController(new HibernateController()).readCommand();
    }
}
