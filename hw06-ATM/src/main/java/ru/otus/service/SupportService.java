package ru.otus.service;

import ru.otus.entity.Cash;
import ru.otus.entity.Cassette;

public interface SupportService {
    Cassette initializeWithEmptyCellsForMoney();

    Cassette initializeWithFilledCellsWithMoney();

    Long getAvailableAmount(Cassette cassette);

    Cash withdraw(Long expectedAmount, Cassette cassette);

    void deposit(Cash cash, Cassette cassette);
}
