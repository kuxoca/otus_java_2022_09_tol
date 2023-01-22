package ru.otus.service;

import ru.otus.entity.Banknote;

public interface CellMoneyService {
    Long getCountBanknote();

    void addBanknote(Long add);

    Banknote getBanknoteType();

    void takeBanknote(Long take);

}
