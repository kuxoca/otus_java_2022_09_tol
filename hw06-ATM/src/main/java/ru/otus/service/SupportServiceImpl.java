package ru.otus.service;

import ru.otus.entity.Banknote;
import ru.otus.entity.Cash;
import ru.otus.entity.Cassette;
import ru.otus.entity.CellMoney;
import ru.otus.exception.Myexception;

import java.util.*;

public class SupportServiceImpl implements SupportService {
    @Override
    public Cassette initializeWithEmptyCellsForMoney() {
        List<CellMoney> cells = new ArrayList<>();
        cells.add(new CellMoney(Banknote.BANKNOTE5000, 0L));
        cells.add(new CellMoney(Banknote.BANKNOTE1000, 0L));
        cells.add(new CellMoney(Banknote.BANKNOTE100, 0L));
        cells.add(new CellMoney(Banknote.BANKNOTE100, 0L));
        cells.add(new CellMoney(Banknote.BANKNOTE10, 0L));
        return new Cassette(cells);
    }

    @Override
    public Cassette initializeWithFilledCellsWithMoney() {
        List<CellMoney> cells = new ArrayList<>();
        cells.add(new CellMoney(Banknote.BANKNOTE5000, 5L));
        cells.add(new CellMoney(Banknote.BANKNOTE1000, 10L));
        cells.add(new CellMoney(Banknote.BANKNOTE100, 20L));
        cells.add(new CellMoney(Banknote.BANKNOTE100, 25L));
        cells.add(new CellMoney(Banknote.BANKNOTE10, 8L));
        return new Cassette(cells);
    }

    @Override
    public CellMoney getCellByBanknoteType(Banknote type, Cassette cassette) {
        return cassette.getCassette().stream().filter(el -> el.getBanknoteType().equals(type))
                .findFirst().orElseThrow(() -> new Myexception("В ATM нет ячееки для приема этого наминала банкнот: " + type.toString()));
    }

    @Override
    public Long getAvailableAmount(Cassette cassette) {
        return cassette.getCassette().stream().mapToLong(CellMoney::getAvailableAmount).sum();
    }

    @Override
    public Cash withdraw(Long expectedAmount, Cassette cassette) {
        Cash cash = new Cash();
        Map<Banknote, Long> map = cash.getCash();

        if (this.getAvailableAmount(cassette) < expectedAmount) {
            throw new Myexception("недостаточно средств");
        } else {
            cassette.getCassette().sort(Comparator.comparingInt(o -> o.getBanknoteType().getDenomination()));
            Collections.reverse(cassette.getCassette());

            for (CellMoney cell : cassette.getCassette()) {
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
                throw new Myexception("Не удалось снять деньги");
            }
            return cash;
        }
    }
}
