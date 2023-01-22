package ru.otus.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class Cassette {
    List<CellMoney> cassette;

    public Cassette(List<CellMoney> cassette) {
        this.cassette = cassette;
    }

    public boolean isEmpty() {
        return cassette.isEmpty();
    }
}
