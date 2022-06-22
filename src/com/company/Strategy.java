package com.company;

public enum Strategy {
    GOLD("Gold") {
        @Override
        public void generateCode(Product product) {
            product.setHonoraryCode(generator.makeCode(getValue() + "-" + product.getId()));
        }
    },
    SILVER("Silver") {
        @Override
        public void generateCode(Product product) {
            product.setHonoraryCode(generator.makeCode(getValue() + "-" + product.getId()));
        }
    },
    BRONZE("Bronze") {
        @Override
        public void generateCode(Product product) {
            product.setHonoraryCode(generator.makeCode(getValue() + "-" + product.getId()));
        }
    };

    private String value;
    protected CodeGenerator generator = new CodeGenerator();

    Strategy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public abstract void generateCode(Product product);
}
