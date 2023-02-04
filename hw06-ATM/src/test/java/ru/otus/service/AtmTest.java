package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.entity.BanknoteDenomination;
import ru.otus.entity.CellMoney;
import ru.otus.entity.StackOfBanknotes;
import ru.otus.exception.MyException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AtmTest {
    static final List<CellMoney> emptyCassette = Arrays.asList(
            new CellMoney(BanknoteDenomination.BANKNOTE5000, 0L),
            new CellMoney(BanknoteDenomination.BANKNOTE1000, 0L),
            new CellMoney(BanknoteDenomination.BANKNOTE100, 0L),
            new CellMoney(BanknoteDenomination.BANKNOTE100, 0L),
            new CellMoney(BanknoteDenomination.BANKNOTE10, 0L)
    );

    static final List<CellMoney> cassetteWithMoney = Arrays.asList(
            new CellMoney(BanknoteDenomination.BANKNOTE5000, 5L),
            new CellMoney(BanknoteDenomination.BANKNOTE1000, 10L),
            new CellMoney(BanknoteDenomination.BANKNOTE100, 20L),
            new CellMoney(BanknoteDenomination.BANKNOTE100, 25L),
            new CellMoney(BanknoteDenomination.BANKNOTE10, 8L)
    );

    @DisplayName("Тест депозита - касета содержить нужные ячейки")
    @Test
    void deposit1() {
        Atm atm = new AtmImpl(emptyCassette);

        StackOfBanknotes stackOfBanknotes = new StackOfBanknotes();
        stackOfBanknotes.setCash(Collections.singletonMap(BanknoteDenomination.BANKNOTE1000, 1L));
        atm.deposit(stackOfBanknotes);
        assertThat(atm.getAvailableAmount()).isEqualTo(1000L);
    }

    @DisplayName("Тест депозита - касета Empty")
    @Test
    void deposit4() {
        Atm atm = new AtmImpl(Collections.emptyList());
        StackOfBanknotes stackOfBanknotes = new StackOfBanknotes();
        stackOfBanknotes.setCash(Collections.singletonMap(BanknoteDenomination.BANKNOTE500, 1L));
        assertThrows(MyException.class, () -> atm.deposit(stackOfBanknotes));
    }

    @DisplayName("Тест депозита - касета null")
    @Test
    void deposit3() {
        Atm atm = new AtmImpl(null);
        StackOfBanknotes stackOfBanknotes = new StackOfBanknotes();
        stackOfBanknotes.setCash(Collections.singletonMap(BanknoteDenomination.BANKNOTE500, 1L));
        assertThrows(MyException.class, () -> atm.deposit(stackOfBanknotes));
    }

    @DisplayName("Тест депозита - касета не содержить нужные ячейки")
    @Test
    void deposit2() {
        Atm atm = new AtmImpl(emptyCassette);

        StackOfBanknotes stackOfBanknotes = new StackOfBanknotes();
        stackOfBanknotes.setCash(Collections.singletonMap(BanknoteDenomination.BANKNOTE500, 1L));
        assertThrows(MyException.class, () -> atm.deposit(stackOfBanknotes));
    }

    @DisplayName("Тест снятия наличных - удачное снятие")
    @Test
    void withdraw1() {

        Atm atm = new AtmImpl(cassetteWithMoney);
        assertThat(atm.withdraw(5550L).getCash())
                .containsEntry(BanknoteDenomination.BANKNOTE5000, 1L)
                .containsEntry(BanknoteDenomination.BANKNOTE100, 5L)
                .containsEntry(BanknoteDenomination.BANKNOTE10, 5L);
    }

    @DisplayName("Тест снятия наличных - ошибка при снятии")
    @Test
    void withdraw2() {
        Atm atm = new AtmImpl(cassetteWithMoney);
        assertThrows(MyException.class, () -> atm.withdraw(5590L));
    }

    @DisplayName("Тест проверки остатка")
    @Test
    void getAvailableAmount() {
        Atm atm = new AtmImpl(cassetteWithMoney);
        assertThat(atm.getAvailableAmount()).isEqualTo(39580L);
    }
}