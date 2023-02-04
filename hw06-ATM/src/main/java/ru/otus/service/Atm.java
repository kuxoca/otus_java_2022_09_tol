package ru.otus.service;

import ru.otus.entity.StackOfBanknotes;

public interface Atm {
    Long getAvailableAmount();

    StackOfBanknotes withdraw(Long expectedAmount);

    void deposit(StackOfBanknotes stackOfBanknotes);
}
