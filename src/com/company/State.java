package com.company;

import java.util.Random;

public enum State {
    IN_STOCK("in_stock") {
        @Override
        public void startSale(Product product) throws Exception {
            product.setStateObj(State.FOR_SALE);
            System.out.println("Start sale");
        }

        @Override
        public void raisePrice(Product product) throws Exception {
            throw new Exception("товар еще не участвует в торгах");
        }

        @Override
        public void withdraw(Product product) throws Exception {
            throw new Exception("нельзя снять с торгов товар, который в них не участвует");
        }

        @Override
        public void giveToTheWinner(Product product) throws Exception {
            throw new Exception("нельзя отдать товар сразу со склада");
        }
    },
    FOR_SALE("for_sale") {
        @Override
        public void startSale(Product product) throws Exception {
            throw new Exception("товар уже участвует в торгах");
        }

        @Override
        public void raisePrice(Product product) throws Exception {
            product.setCurrentPrice(product.getCurrentPrice() + ((new Random().nextInt(10) + 1) * BID));
            System.out.println("BID success");
        }

        @Override
        public void withdraw(Product product) throws Exception {
            if (product.getCurrentPrice() == product.getPrice()) {
                product.setStateObj(State.IN_STOCK);
                System.out.println("Product return to stock");
            } else {
                throw new Exception("товар уже в резерве, можно только выдать");
            }
        }

        @Override
        public void giveToTheWinner(Product product) throws Exception {
            if (product.getCurrentPrice() == product.getPrice()) {
                throw new Exception("нельзя отдать товар бесплатно");
            }
            if (product.getCurrentPrice() > product.getPrice()) {
                product.setStateObj(State.SOLD);
                if (product.getCurrentPrice() - product.getPrice() >= 1000){
                    Strategy.GOLD.generateCode(product);
                }
                if (product.getCurrentPrice() - product.getPrice() >= 500
                        && product.getCurrentPrice() - product.getPrice() < 1000){
                    Strategy.SILVER.generateCode(product);
                }
                if (product.getCurrentPrice() - product.getPrice() < 500) {
                    Strategy.BRONZE.generateCode(product);
                }
                System.out.println("Product is sold");
            }
        }
    },
    SOLD("sold") {
        @Override
        public void startSale(Product product) throws Exception {
            throw new Exception("нельзя начать продажу, так как товар уже продан");
        }

        @Override
        public void raisePrice(Product product) throws Exception {
            throw new Exception("нельзя повысить стоимость, так как товар уже продан");
        }

        @Override
        public void withdraw(Product product) throws Exception {
            throw new Exception("нельзя снять с торгов, так как товар уже продан");
        }

        @Override
        public void giveToTheWinner(Product product) throws Exception {
            throw new Exception("нельзя выдать покупателю, так как товар уже выдан");
        }
    };

    private String value;
    protected static final double BID = 100.0;

    public String getValue() {
        return value;
    }

    State(String value) {
        this.value = value;
    }

    public abstract void startSale(Product product) throws Exception;

    public abstract void raisePrice(Product product) throws Exception;

    public abstract void withdraw(Product product) throws Exception;

    public abstract void giveToTheWinner(Product product) throws Exception;
}
