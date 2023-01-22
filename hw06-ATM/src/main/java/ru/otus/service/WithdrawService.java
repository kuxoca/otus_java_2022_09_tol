package ru.otus.service;

import ru.otus.entity.Cash;

public interface WithdrawService {
    Cash withdrawCash(Long expectedAmount);

}
