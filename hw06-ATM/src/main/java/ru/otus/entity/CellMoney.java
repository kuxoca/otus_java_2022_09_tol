package ru.otus.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.otus.exception.Myexception;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class CellMoney {
    final Banknote banknote;
    Long count;

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

    public Long getAvailableAmount() {
        return banknote.getDenomination() * count;
    }
}
