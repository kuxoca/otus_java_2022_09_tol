package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.entity.BanknoteDenomination;
import ru.otus.entity.CellMoney;
import ru.otus.exception.MyException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CellMoneyTest {

    CellMoney cellMoney;

    @BeforeEach
    void beforeEach() {
//        cellMoney = null;
    }

    @DisplayName("Проверка на добавления банкнот в ячейку")
    @Test
    void addBanknote() {
        CellMoney cellMoney = new CellMoney(BanknoteDenomination.BANKNOTE5000);
        cellMoney.addBanknote(10L);
        assertThat(cellMoney.getCountBanknote()).isEqualTo(10L);
    }

    @DisplayName("Проверка на изьятие банктнот из ячейки")
    @Test
    void takeBanknote1() {
        cellMoney = new CellMoney(BanknoteDenomination.BANKNOTE1000);
        cellMoney.addBanknote(10L);
        cellMoney.takeBanknote(3L);
        assertThat(cellMoney.getCountBanknote()).isEqualTo(7L);
    }

    @DisplayName("Проверка на изьятие банктнот из ячейки - ошибка")
    @Test
    void takeBanknote2() {
        cellMoney = new CellMoney(BanknoteDenomination.BANKNOTE1000);
        cellMoney.addBanknote(10L);
        assertThrows(MyException.class, () -> cellMoney.takeBanknote(13L));
    }

    @DisplayName("Проверка на получение банкноты нужнного наминала")
    @Test
    void getBanknoteType() {
        CellMoney cellMoney = new CellMoney(BanknoteDenomination.BANKNOTE1000);
        CellMoney cellMoney1 = new CellMoney(BanknoteDenomination.BANKNOTE5000);
        assertThat(cellMoney.getBanknoteType()).isEqualTo(BanknoteDenomination.BANKNOTE1000);
        assertThat(cellMoney1.getBanknoteType()).isEqualTo(BanknoteDenomination.BANKNOTE5000);
    }
}