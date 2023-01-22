package ru.otus;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.entity.Banknote;
import ru.otus.entity.Cash;
import ru.otus.entity.Cassette;
import ru.otus.entity.CellMoney;
import ru.otus.exception.Myexception;

import java.util.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class AtmOldTest {
    private static AtmOld atmOld;

    @BeforeAll
    static void setup() {
//        atm = new Atm();
    }

    @BeforeEach
    void beforeEach() {
        Cassette cassette = new Cassette();
        cassette.setCassette(
                List.of(
                ));
        atmOld = new AtmOld(cassette);
    }

    @DisplayName("Проверка остатка денег в АТМ")
    @Test
    void getAvailableAmount() {
        atmOld.initializeWithFilledCellsWithMoney();
        assertThat(atmOld.getAvailableAmount()).isEqualTo(39530L);
    }

    @DisplayName("Тест приема наличных - успешный прием")
    @Test
    void testBanknoteTake() {
        atmOld.initializeWithEmptyCellsForMoney();

        Map<Banknote, Long> map = new HashMap<>();
        map.put(Banknote.BANKNOTE1000, 10L);
        map.put(Banknote.BANKNOTE5000, 5L);
        map.put(Banknote.BANKNOTE100, 1L);

        atmOld.depositCash(new Cash(map));

        assertThat(atmOld.getCellByBanknoteType(Banknote.BANKNOTE1000).getCountBanknote()).isEqualTo(10L);
        assertThat(atmOld.getCellByBanknoteType(Banknote.BANKNOTE5000).getCountBanknote()).isEqualTo(5L);
        assertThat(atmOld.getCellByBanknoteType(Banknote.BANKNOTE100).getCountBanknote()).isEqualTo(1L);
    }

    @DisplayName("Тест приема банкнот - Ошибка есть нет ячейки")
    @Test()
    void testBanknote() {
        atmOld.initializeWithEmptyCellsForMoney();

        Map<Banknote, Long> map = new HashMap<>();
        map.put(Banknote.BANKNOTE500, 10L);
        map.put(Banknote.BANKNOTE5000, 5L);
        map.put(Banknote.BANKNOTE1000, 1L);
        assertThrows(Myexception.class, () -> atmOld.depositCash(new Cash(map)));
    }

    @DisplayName("Тест инициализации АТМ по умолчанию")
    @Test
    void testInitAtm() {

        atmOld.initializeWithFilledCellsWithMoney();

        List<CellMoney> cellMonies = Arrays.asList(
                new CellMoney(Banknote.BANKNOTE5000, 5L),
                new CellMoney(Banknote.BANKNOTE1000, 10L),
                new CellMoney(Banknote.BANKNOTE100, 20L),
                new CellMoney(Banknote.BANKNOTE100, 25L),
                new CellMoney(Banknote.BANKNOTE10, 3L)
        );

        assertThat(cellMonies).hasSameElementsAs(atmOld.getCassette().getCassette());
    }

    @DisplayName("Тест снятия денег - успешное снятие")
    @Test
    void withdraw() {
        atmOld.initializeWithFilledCellsWithMoney();
        assertThat(atmOld.withdrawCash(5000L).getCash()).isEqualTo(Collections.singletonMap(Banknote.BANKNOTE5000, 1L));
    }

    @DisplayName("Тест снятия денег - ошибка 'Не удалось снять деньги'")
    @Test
    void withdraw3() {
        atmOld.initializeWithFilledCellsWithMoney();
        assertThrows(Myexception.class, () -> atmOld.withdrawCash(5540L));
    }

    @DisplayName("Тест снятия денег - ошибка 'недостаточно средств'")
    @Test
    void withdraw2() {
        atmOld.initializeWithEmptyCellsForMoney();
        assertThrows(Myexception.class, () -> atmOld.withdrawCash(5000L));
    }
}
