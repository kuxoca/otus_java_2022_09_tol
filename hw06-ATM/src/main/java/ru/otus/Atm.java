package ru.otus;

import ru.otus.entity.Banknote;
import ru.otus.entity.CellMoney;
import ru.otus.entity.WithdrawMoney;
import ru.otus.exception.Myexception;
import ru.otus.service.DepositService;
import ru.otus.service.InformationService;
import ru.otus.service.SupportService;
import ru.otus.service.WithdrawService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Atm implements InformationService, DepositService, WithdrawService, SupportService {
    private final List<CellMoney> cells = new ArrayList<>();


    public CellMoney getCellByBanknoteType(Banknote type) {
        return cells.stream().filter(el -> el.getBanknoteType().equals(type))
                .findFirst().orElseThrow();
    }

    @Override
    public void depositMoney(Banknote banknote, Long count) {
        if (cells.isEmpty()) {
            throw new Myexception("В ATM нет ячеек для приема этого наминала банкнот");
        }
        getCellByBanknoteType(banknote).addBanknote(count);
    }

    @Override
    public List<WithdrawMoney> withdraw(Long expectedAmount) {
        if (this.getAvailableAmount() < expectedAmount) {
            throw new Myexception("недостаточно средств");
        } else {
            List<WithdrawMoney> moneyList = new ArrayList<>();
            cells.sort(Comparator.comparingInt(o -> o.getBanknoteType().getDenomination()));
            Collections.reverse(cells);

            for (CellMoney cell : cells) {
                long x = expectedAmount / cell.getBanknoteType().getDenomination();
                if (x > 0) {
                    if (cell.getCountBanknote() >= x) {
                        expectedAmount -= (cell.getBanknoteType().getDenomination() * x);
                        moneyList.add(new WithdrawMoney(cell.getBanknoteType(), x));
                        cell.takeBanknote(x);
                    } else {
                        expectedAmount -= cell.getBanknoteType().getDenomination() * cell.getCountBanknote();
                        moneyList.add(new WithdrawMoney(cell.getBanknoteType(), cell.getCountBanknote()));
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
            return moneyList;
        }
    }


    @Override
    public Long getAvailableAmount() {
        return cells.stream().mapToLong(CellMoney::getAvailableAmount).sum();
//        return cells.stream().mapToLong(el -> el.getBanknoteType().getDenomination() * el.getCountBanknote()).sum();
    }


    @Override
    public void initializeWithEmptyCellsForMoney() {
        cells.add(new CellMoney(Banknote.BANKNOTE5000, 0L));
        cells.add(new CellMoney(Banknote.BANKNOTE1000, 0L));
        cells.add(new CellMoney(Banknote.BANKNOTE100, 0L));
        cells.add(new CellMoney(Banknote.BANKNOTE100, 0L));
        cells.add(new CellMoney(Banknote.BANKNOTE10, 0L));
    }

    @Override
    public void initializeWithFilledCellsWithMoney() {
        cells.add(new CellMoney(Banknote.BANKNOTE5000, 5L));
        cells.add(new CellMoney(Banknote.BANKNOTE1000, 10L));
        cells.add(new CellMoney(Banknote.BANKNOTE100, 20L));
        cells.add(new CellMoney(Banknote.BANKNOTE100, 25L));
        cells.add(new CellMoney(Banknote.BANKNOTE10, 3L));
    }

    public List<CellMoney> getCells() {
        return cells;
    }

    @Override
    public String toString() {
        return "Atm{" +
                "cells=" + cells +
                '}';
    }
}
