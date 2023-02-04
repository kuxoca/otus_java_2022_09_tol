package ru.otus.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.otus.exception.MyException;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@ToString
public class CellMoney {
    final BanknoteDenomination banknoteDenomination;
    long count;

    public CellMoney(BanknoteDenomination banknoteDenomination) {
        this.banknoteDenomination = banknoteDenomination;
        this.count = 0L;
    }

    public CellMoney(BanknoteDenomination banknoteDenomination, Long count) {
        this.banknoteDenomination = banknoteDenomination;
        this.count = count;
    }

    public void addBanknote(Long add) {
        count += add;
    }

    public void takeBanknote(Long take) {
        if (count < take) {
            throw new MyException("недостаточно банкнот");
        } else {
            count = count - take;
        }
    }

    public BanknoteDenomination getBanknoteType() {
        return banknoteDenomination;
    }

    public Long getCountBanknote() {
        return count;
    }

    public Long getAvailableAmount() {
        return banknoteDenomination.getDenomination() * count;
    }
}
