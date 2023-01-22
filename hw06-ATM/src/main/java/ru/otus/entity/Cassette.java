package ru.otus.entity;

import java.util.List;
import java.util.Objects;

public class Cassette {
    private List<CellMoney> cassette;

    public Cassette(List<CellMoney> cassette) {
        this.cassette = cassette;
    }

    public void addMoney(Banknote banknote, Long aLong) {
    }

    public Cassette() {
    }

    public List<CellMoney> getCassette() {
        return cassette;
    }

    public void setCassette(List<CellMoney> cassette) {
        this.cassette = cassette;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cassette cassette1 = (Cassette) o;
        return Objects.equals(cassette, cassette1.cassette);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cassette);
    }

    @Override
    public String toString() {
        return "Cassette{" +
                "cassette=" + cassette +
                '}';
    }

    public boolean isEmpty() {
        return cassette.isEmpty();
    }
}
