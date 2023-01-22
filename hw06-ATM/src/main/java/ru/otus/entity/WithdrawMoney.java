package ru.otus.entity;

import java.util.Objects;

public class WithdrawMoney {

    private final Banknote banknote;
    private final Long count;

    public WithdrawMoney(Banknote banknote, Long count) {
        this.banknote = banknote;
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WithdrawMoney that = (WithdrawMoney) o;
        return banknote == that.banknote && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(banknote, count);
    }

    @Override
    public String toString() {
        return "WithdrawMoney{" +
                "banknote=" + banknote +
                ", count=" + count +
                '}';
    }
}
