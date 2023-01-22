package ru.otus.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.otus.entity.Cash;
import ru.otus.entity.Cassette;
import ru.otus.exception.Myexception;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class AtmImpl implements Atm {
    Cassette cassette;
    final SupportService service;

    public AtmImpl(SupportService service, boolean empty) {
        this.service = service;
        if (empty) {
            this.cassette = service.initializeWithEmptyCellsForMoney();
        } else {
            this.cassette = service.initializeWithFilledCellsWithMoney();
        }
    }

    public AtmImpl(SupportService service) {
        this.service = service;
        this.cassette = service.initializeWithFilledCellsWithMoney();

    }

    @Override
    public void deposit(Cash cash) {
        if (cassette.isEmpty()) {
            throw new Myexception("В ATM нет ячеек для приема банкнот");
        }
        cash.getCash().forEach((k, v) ->
                service.getCellByBanknoteType(k, cassette).addBanknote(v));
    }

    @Override
    public Cash withdraw(Long expectedAmount) {
        return service.withdraw(expectedAmount, cassette);
    }

    @Override
    public Long getAvailableAmount() {
        return service.getAvailableAmount(cassette);

    }
}






































