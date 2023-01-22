package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.entity.Banknote;
import ru.otus.entity.Cash;
import ru.otus.exception.Myexception;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AtmTest {
    SupportService service = new SupportServiceImpl();
    Atm atm;

    @DisplayName("Тест депозита - касета содержить нужные ячейки")
    @Test
    void deposit1() {
        atm = new AtmImpl(service, true);
        Cash cash = new Cash();
        cash.setCash(Collections.singletonMap(Banknote.BANKNOTE1000, 1L));
        atm.deposit(cash);
        assertThat(atm.getAvailableAmount()).isEqualTo(1000L);
    }

    @DisplayName("Тест депозита - касета не содержить нужные ячейки")
    @Test
    void deposit2() {
        atm = new AtmImpl(service, true);
        Cash cash = new Cash();
        cash.setCash(Collections.singletonMap(Banknote.BANKNOTE500, 1L));
        assertThrows(Myexception.class, () -> atm.deposit(cash));
    }

    @DisplayName("Тест снятия наличных - удачное снятие")
    @Test
    void withdraw1() {
        atm = new AtmImpl(service);
        assertThat(atm.withdraw(5550L).getCash())
                .containsEntry(Banknote.BANKNOTE5000, 1L)
                .containsEntry(Banknote.BANKNOTE100, 5L)
                .containsEntry(Banknote.BANKNOTE10, 5L);
    }

    @DisplayName("Тест снятия наличных - ошибка при снятии")
    @Test
    void withdraw2() {
        atm = new AtmImpl(service, false);
        assertThrows(Myexception.class, () -> atm.withdraw(5590L));
    }

    @DisplayName("Тест проверки остатка")
    @Test
    void getAvailableAmount() {
        atm = new AtmImpl(service, false);
        assertThat(atm.getAvailableAmount()).isEqualTo(39580L);
    }
}