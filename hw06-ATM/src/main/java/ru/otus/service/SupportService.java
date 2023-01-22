package ru.otus.service;

import ru.otus.entity.Banknote;
import ru.otus.entity.Cash;
import ru.otus.entity.Cassette;
import ru.otus.entity.CellMoney;

public interface SupportService {
    Cassette initializeWithEmptyCellsForMoney();

    Cassette initializeWithFilledCellsWithMoney();

    CellMoney getCellByBanknoteType(Banknote type, Cassette cassette);

    Long getAvailableAmount(Cassette cassette);

    Cash withdraw(Long expectedAmount, Cassette cassette);
}
