package ru.otus.entity;

import java.util.HashMap;
import java.util.Map;

public class Cash  {
    private Map<Banknote, Long> cash = new HashMap<>();

    public Cash(Map<Banknote, Long> cash) {
        this.cash = cash;
    }

    public Cash() {
    }

    public Map<Banknote, Long> getCash() {
        return cash;
    }

    public void setCash(Map<Banknote, Long> cash) {
        this.cash = cash;
    }

    public Long getAvailableAmount() {
        return cash.entrySet().stream().mapToLong(value -> value.getKey().getDenomination() * value.getValue()).sum();
    }

    @Override
    public String toString() {
        return "Cash{" +
                "cash=" + cash +
                '}';
    }
}
