package ru.otus.entity;

public enum Banknote {
    BANKNOTE10(10),
    BANKNOTE100(100),
    BANKNOTE200(200),
    BANKNOTE500(500),
    BANKNOTE1000(1000),
    BANKNOTE5000(5000);

    private final int denomination;

    Banknote(Integer denomination) {
        this.denomination = denomination;
    }

    public int getDenomination() {
        return denomination;
    }
}
