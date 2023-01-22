package ru.otus.service;

import ru.otus.entity.WithdrawMoney;

import java.util.List;

public interface WithdrawService {
    List<WithdrawMoney> withdraw(Long expectedAmount);

}
