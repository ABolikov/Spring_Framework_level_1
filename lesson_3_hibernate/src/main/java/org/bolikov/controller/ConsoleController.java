package org.bolikov.controller;

import org.bolikov.Customer;
import org.bolikov.Product;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleController {

    private HibernateController controller;

    private boolean isCommand = true;
    private boolean addC = false;
    private boolean addP = false;
    private boolean market = false;
    private boolean delP = false;
    private boolean delC = false;
    private boolean customer = false;
    private boolean product = false;
    private boolean setCost = false;

    public ConsoleController(HibernateController controller) {
        this.controller = controller;
        infoCommand();
    }

    private void infoCommand() {
        System.out.println("Доступны следующие команды (регистр не важен):");
        System.out.println("addC - добавление покупателя");
        System.out.println("delC - удаление покупателя");
        System.out.println("addP - добавление продукта");
        System.out.println("setCost - изменение цены продукта");
        System.out.println("delP - удаление продукта");
        System.out.println("market - добавление товара который купил покупатель");
        System.out.println("customer - вывести, что купил указанны покупатель");
        System.out.println("product - вывести, кто покупал указанный продукт");
        System.out.println("help - выведение списка доступных команд (доступно только при вводе команд)");
        System.out.println("back - возврат в меню ввода команд (доступно только при вводе значений)");
        System.out.println("exit - выход из приложения (доступно только при вводе команд)");
    }

    public void readCommand() {
        Scanner in = new Scanner(System.in);
        while (in != null) {
            String command = in.nextLine().toLowerCase();
            if (isCommand) {
                switch (command) {
                    case "addc": {
                        System.out.println("Введите имя нового покупателя");
                        isCommand = false;
                        addC = true;
                        break;
                    }
                    case "delc": {
                        System.out.println("Для удалим покупателя введите его имя");
                        isCommand = false;
                        delC = true;
                        break;
                    }
                    case "addp": {
                        System.out.println("Введите наименование товара и его цену, через запятую." +
                                "\nДробная часть стоимости указывается через точку");
                        isCommand = false;
                        addP = true;
                        break;
                    }
                    case "setcost": {
                        System.out.println("Введите наименование товара и его новую цену, через запятую." +
                                "\nДробная часть стоимости указывается через точку");
                        isCommand = false;
                        setCost = true;
                        break;
                    }
                    case "delp": {
                        System.out.println("Для удаления продукта введите его наименование");
                        isCommand = false;
                        delP = true;
                        break;
                    }
                    case "market": {
                        System.out.println("Введите имя покупателя и наименование товара, через запятую");
                        isCommand = false;
                        market = true;
                        break;
                    }
                    case "customer": {
                        System.out.println("Введите имя покупателя для которго необходимо просмотреть списко покупок");
                        isCommand = false;
                        customer = true;
                        break;
                    }
                    case "product": {
                        System.out.println("Введите наименование товара для которго необходим просмотреть списко покупателей");
                        isCommand = false;
                        product = true;
                        break;
                    }
                    case "help": {
                        infoCommand();
                        break;
                    }
                    case "exit": {
                        in.close();
                        in = null;
                        System.out.println("Выход из консоли выполнен");
                        break;
                    }
                    default: {
                        System.out.println("Указана не существующая команда.");
                        infoCommand();
                    }
                }
            } else {
                if (command.equalsIgnoreCase("back")) trueBooleanCommand();
                else setValueHibernate(command.split(","));
            }
        }
    }

    private void setValueHibernate(String... value) {
        boolean error = false;
        if (value.length > 2) {
            System.out.println("Допущена ошибка при вводе значения, повторите попытку:");
        } else {
            if (addC) controller.addCustomer(value[0]);
            if (addP) {
                if (value.length < 2) {
                    System.out.println("Допущена ошибка при вводе значения, повторите попытку:");
                    error = true;
                } else {
                    controller.addProduct(value[0], BigDecimal.valueOf(Double.parseDouble(value[1])));
                }
            }
            if (delC) controller.deleteCustomer(value[0]);
            if (delP) controller.deleteProduct(value[0]);
            if (market) {
                if (value.length < 2) {
                    System.out.println("Допущена ошибка при вводе значения, повторите попытку:");
                    error = true;
                } else {
                    controller.addMarket(value[0], value[1]);
                }
            }
            if (customer) {
                Customer object = controller.selectCustomer(value[0], true);
                if (object != null) System.out.println(object.getOrderItems());
            }
            if (product) {
                Product object = controller.selectProduct(value[0], true);
                if (object != null) System.out.println(object.getOrderItems());
            }
            if (setCost) {
                controller.setCostProduct(value[0], BigDecimal.valueOf(Double.parseDouble(value[1])));
            }
            if (!error) {
                System.out.println("Введите команду (регистр не важен):");
                trueBooleanCommand();
            }
        }
    }

    private void trueBooleanCommand() {
        isCommand = true;
        addC = false;
        addP = false;
        market = false;
        delP = false;
        delC = false;
        customer = false;
        product = false;
        setCost = false;
    }
}
