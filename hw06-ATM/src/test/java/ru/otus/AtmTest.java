package ru.otus;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.entity.Banknote;
import ru.otus.entity.CellMoney;
import ru.otus.entity.WithdrawMoney;
import ru.otus.exception.Myexception;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class AtmTest {
    private static Atm atm;

    @BeforeAll
    static void setup() {
        atm = new Atm();
    }

    @BeforeEach
    void beforeEach() {
        atm = new Atm();
    }

    @DisplayName("Проверка остатка денег в АТМ")
    @Test
    void getAvailableAmount() {
        atm.initializeWithFilledCellsWithMoney();
        assertThat(atm.getAvailableAmount()).isEqualTo(39530L);
    }

    @DisplayName("Тест приема банкнот - успешный прием")
    @Test
    void testBanknoteTake() {
        atm.initializeWithEmptyCellsForMoney();
        atm.depositMoney(Banknote.BANKNOTE1000, 10L);
        atm.depositMoney(Banknote.BANKNOTE100, 1L);
        atm.depositMoney(Banknote.BANKNOTE5000, 5L);
        assertThat(atm.getCellByBanknoteType(Banknote.BANKNOTE1000).getCountBanknote()).isEqualTo(10L);
        assertThat(atm.getCellByBanknoteType(Banknote.BANKNOTE5000).getCountBanknote()).isEqualTo(5L);
        assertThat(atm.getCellByBanknoteType(Banknote.BANKNOTE100).getCountBanknote()).isEqualTo(1L);
    }

    @DisplayName("Тест приема банкнот - Ошибка есть нет ячейки")
    @Test()
    void testBanknote() {
        assertThrows(Myexception.class, () -> atm.depositMoney(Banknote.BANKNOTE1000, 10L));
    }

    @DisplayName("Тест инициализации АТМ по умолчанию")
    @Test
    void testInitAtm() {

        atm.initializeWithFilledCellsWithMoney();

        List<CellMoney> cellMonies = Arrays.asList(
                new CellMoney(Banknote.BANKNOTE5000, 5L),
                new CellMoney(Banknote.BANKNOTE1000, 10L),
                new CellMoney(Banknote.BANKNOTE100, 20L),
                new CellMoney(Banknote.BANKNOTE100, 25L),
                new CellMoney(Banknote.BANKNOTE10, 3L)
        );

        assertThat(cellMonies).hasSameElementsAs(atm.getCells());
    }

    @DisplayName("Тест снятия денег - успешное снятие")
    @Test
    void withdraw() {
        atm.initializeWithFilledCellsWithMoney();
//        System.out.println(atm.withdraw(5000L));
        assertThat(atm.withdraw(520L)).hasSameElementsAs(
                List.of(
                        //      new WithdrawMoney(Banknote.BANKNOTE5000, 5L),
//                        new WithdrawMoney(Banknote.BANKNOTE1000,5L),
                        new WithdrawMoney(Banknote.BANKNOTE100, 5L),
                        new WithdrawMoney(Banknote.BANKNOTE10, 2L)
                ));
    }

    @DisplayName("Тест снятия денег - ошибка 'Не удалось снять деньги'")
    @Test
    void withdraw3() {
        atm.initializeWithFilledCellsWithMoney();
//        System.out.println(atm.withdraw(5000L));
        assertThrows(Myexception.class, () -> atm.withdraw(5540L));
    }

    @DisplayName("Тест снятия денег - ошибка 'недостаточно средств'")
    @Test
    void withdraw2() {
        atm.initializeWithEmptyCellsForMoney();
        assertThrows(Myexception.class, () -> atm.withdraw(5000L));
    }
}
