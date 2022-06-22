package com.company;

import java.util.Scanner;

public class Main {
    private static final FileService fileService = new FileService("./products.json");
    private static Product[] products;

    public static void main(String[] args) {

        products = fileService.getGoods();

        for (var p : products) {
            produceProduct(p);
        }

        run();
    }

    private static void run() {
        printInfo(products);

        int number = chooseProduct() - 1;

        chooseAction(products[number]);
    }

    private static int chooseProduct() {
        System.out.print("\nВыберите товар: ");
        return new Scanner(System.in).nextInt();
    }

    private static void chooseAction(Product product) {
        printInfo(product);
        System.out.println("\nВыберите действие:\n" +
                "1. Выставить на аукцион;\n" +
                "2. Поднять цену;\n" +
                "3. Выдать победителю;\n" +
                "4. Снять с торгов;\n" +
                "5. Отобразить информацию о товаре;\n" +
                "6. Вернуться в список товаров;");
        int action = new Scanner(System.in).nextInt();
        switch (action) {
            case 1:
                product.startSale();
                fileService.writeFile(products);
                chooseAction(product);
                break;
            case 2:
                product.raisePrice();
                fileService.writeFile(products);
                chooseAction(product);
                break;
            case 3:
                product.giveToTheWinner();
                fileService.writeFile(products);
                chooseAction(product);
                break;
            case 4:
                product.withdraw();
                fileService.writeFile(products);
                chooseAction(product);
                break;
            case 5:
                chooseAction(product);
                break;
            case 6:
                run();
                break;
            default:
                System.out.println("Incorrect number. Try again...");
                chooseAction(product);
        }
    }

    private static void produceProduct(Product product) {
        product.setCurrentPrice(product.getPrice());
        if (product.getState().equalsIgnoreCase("in_stock")) {
            product.setStateObj(State.IN_STOCK);
        }
        if (product.getState().equalsIgnoreCase("for_sale")) {
            product.setStateObj(State.FOR_SALE);
        }
        if (product.getState().equalsIgnoreCase("sold")) {
            product.setStateObj(State.SOLD);
        }

    }

    private static void printInfo(Product... products) {
        System.out.printf("%n%s | %-15s | %-10s | %-10s | %s%n", "ID", "Name", "State", "Price", "Honorary Code");
        System.out.println("---+-----------------+------------+------------+----------------------");
        for (var p : products) {
            System.out.println(p);
        }

    }
}