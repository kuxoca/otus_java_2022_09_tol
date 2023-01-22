package ru.otus.entity;


import ru.otus.exception.Myexception;

import java.util.Objects;

public class CellMoney {
    private Banknote banknote;
    private Long count;

    private CellMoney() {
    }

    public CellMoney(Banknote banknote) {
        this.banknote = banknote;
        this.count = 0L;
    }

    public CellMoney(Banknote banknote, Long count) {
        this.banknote = banknote;
        this.count = count;
    }

    public void addBanknote(Long add) {
        count += add;
    }

    public void takeBanknote(Long take) {
        if (count < take) {
            throw new Myexception("недостаточно банкнот");
        } else {
            count = count - take;
        }
    }

    public Banknote getBanknoteType() {
        return banknote;
    }


    public Long getCountBanknote() {
        return count;
    }

    @Override
    public String toString() {
        return "CellMoney{" +
                "banknote=" + banknote +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellMoney cellMoney = (CellMoney) o;
        return banknote == cellMoney.banknote && Objects.equals(count, cellMoney.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(banknote, count);
    }

    public Long getAvailableAmount() {
        return banknote.getDenomination() * count;
    }
}
