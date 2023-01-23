package ru.otus.service;

import ru.otus.entity.Cash;

public interface Atm {
    Long getAvailableAmount();

    Cash withdraw(Long expectedAmount);

    void deposit(Cash cash);
}
