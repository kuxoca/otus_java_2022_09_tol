package ru.otus.service;

import ru.otus.entity.Cash;

public interface Atm {
    void deposit(Cash cash);

    Cash withdraw(Long expectedAmount);

    Long getAvailableAmount();
}
