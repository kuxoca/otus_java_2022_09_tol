package ru.otus;

import ru.otus.entity.Banknote;
import ru.otus.entity.Cash;
import ru.otus.entity.Cassette;
import ru.otus.entity.CellMoney;
import ru.otus.exception.Myexception;

import java.util.*;

public class AtmOld {
//    private final List<CellMoney> cells = new ArrayList<>();

    public Cassette getCassette() {
        return cassette;
    }

    public void setCassette(Cassette cassette) {
        this.cassette = cassette;
    }

    private Cassette cassette;

    public AtmOld(Cassette cassette) {
        this.cassette = cassette;
    }

    public CellMoney getCellByBanknoteType(Banknote type) {
        return cassette.getCassette().stream().filter(el -> el.getBanknoteType().equals(type))
                .findFirst().orElseThrow(() -> new Myexception("В ATM нет ячееки для приема этого наминала банкнот: " + type.toString()));
    }

    public void depositCash(Cash cash) {
        if (cassette.getCassette().isEmpty()) {
            throw new Myexception("В ATM нет ячеек для приема этого наминала банкнот");
        }
        cash.getCash().forEach((k, v) -> {
            getCellByBanknoteType(k).addBanknote(v);
        });
    }

    public Cash withdrawCash(Long expectedAmount) {
        Cash cash = new Cash();
        Map<Banknote, Long> map = cash.getCash();

        if (this.getAvailableAmount() < expectedAmount) {
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
//                        moneyList.add(new WithdrawMoney(cell.getBanknoteType(), x));
                        cell.takeBanknote(x);
                    } else {
                        expectedAmount -= cell.getBanknoteType().getDenomination() * cell.getCountBanknote();
                        map.put(cell.getBanknoteType(), cell.getCountBanknote());
//                        moneyList.add(new WithdrawMoney(cell.getBanknoteType(), cell.getCountBanknote()));
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


    public Long getAvailableAmount() {
        return cassette.getCassette().stream().mapToLong(CellMoney::getAvailableAmount).sum();
//        return cells.stream().mapToLong(el -> el.getBanknoteType().getDenomination() * el.getCountBanknote()).sum();
    }


    public void initializeWithEmptyCellsForMoney() {
        List<CellMoney> cells = new ArrayList<>();
        cells.add(new CellMoney(Banknote.BANKNOTE5000, 0L));
        cells.add(new CellMoney(Banknote.BANKNOTE1000, 0L));
        cells.add(new CellMoney(Banknote.BANKNOTE100, 0L));
        cells.add(new CellMoney(Banknote.BANKNOTE100, 0L));
        cells.add(new CellMoney(Banknote.BANKNOTE10, 0L));
        cassette.setCassette(cells);
    }

    public void initializeWithFilledCellsWithMoney() {
        List<CellMoney> cells = new ArrayList<>();
        cells.add(new CellMoney(Banknote.BANKNOTE5000, 5L));
        cells.add(new CellMoney(Banknote.BANKNOTE1000, 10L));
        cells.add(new CellMoney(Banknote.BANKNOTE100, 20L));
        cells.add(new CellMoney(Banknote.BANKNOTE100, 25L));
        cells.add(new CellMoney(Banknote.BANKNOTE10, 3L));
        cassette.setCassette(cells);
    }

    @Override
    public String toString() {
        return "Atm{" +
                "cassette=" + cassette +
                '}';
    }
}
