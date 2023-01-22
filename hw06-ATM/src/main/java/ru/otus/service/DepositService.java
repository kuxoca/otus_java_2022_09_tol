package ru.otus.service;

import ru.otus.entity.Banknote;

public interface DepositService {
    void depositMoney(Banknote banknote, Long count);

}
