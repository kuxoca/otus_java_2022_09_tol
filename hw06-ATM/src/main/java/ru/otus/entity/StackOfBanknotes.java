package ru.otus.entity;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
public class StackOfBanknotes {
    private Map<BanknoteDenomination, Long> cash;

    public StackOfBanknotes() {
        cash = new HashMap<>();
    }

    public Map<BanknoteDenomination, Long> getCash() {
        return cash;
    }

    public void setCash(Map<BanknoteDenomination, Long> cash) {
        this.cash = cash;
    }
}
