package ru.otus.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.otus.entity.BanknoteDenomination;
import ru.otus.entity.CellMoney;
import ru.otus.entity.StackOfBanknotes;
import ru.otus.exception.MyException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class AtmImpl implements Atm {
    List<CellMoney> cassette;

    private AtmImpl() {
    }

    public AtmImpl(List<CellMoney> cassette) {
        this.cassette = cassette;
    }

    @Override
    public void deposit(StackOfBanknotes stackOfBanknotes) {
        if (cassette.isEmpty()) {
            throw new MyException("В ATM нет ячеек для приема банкнот");
        }
        stackOfBanknotes.getCash().forEach((k, v) ->
                getCellByBanknoteType(k, cassette).addBanknote(v));
    }

    @Override
    public StackOfBanknotes withdraw(Long expectedAmount) {
//        return service.withdraw(expectedAmount, cassette);
        if (getAvailableAmount() < expectedAmount) {
            throw new MyException("недостаточно средств");
        } else {
            StackOfBanknotes stackOfBanknotes = new StackOfBanknotes();
            Map<BanknoteDenomination, Long> map = stackOfBanknotes.getCash();
            cassette.sort(Comparator.comparingInt(o -> o.getBanknoteType().getDenomination()));
            Collections.reverse(cassette);
            for (CellMoney cell : cassette) {
                long x = expectedAmount / cell.getBanknoteType().getDenomination();
                if (x > 0) {
                    if (cell.getCountBanknote() >= x) {
                        expectedAmount -= (cell.getBanknoteType().getDenomination() * x);
                        map.put(cell.getBanknoteType(), x);
                        cell.takeBanknote(x);
                    } else {
                        expectedAmount -= cell.getBanknoteType().getDenomination() * cell.getCountBanknote();
                        map.put(cell.getBanknoteType(), cell.getCountBanknote());
                        cell.takeBanknote(cell.getCountBanknote());
                    }
                }
                if (expectedAmount == 0) {
                    break;
                }
            }
            if (expectedAmount != 0) {
                throw new MyException("Не удалось снять деньги");
            }
            return stackOfBanknotes;
        }
    }

    @Override
    public Long getAvailableAmount() {
//        return service.getAvailableAmount(cassette);
        return cassette.stream().mapToLong(CellMoney::getAvailableAmount).sum();

    }

    private CellMoney getCellByBanknoteType(BanknoteDenomination type, List<CellMoney> cassette) {
        return cassette.stream().filter(el -> el.getBanknoteType().equals(type))
                .findFirst().orElseThrow(() -> new MyException("В ATM нет ячееки для приема этого наминала банкнот: " + type.toString()));
    }
}