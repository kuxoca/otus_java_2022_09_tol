package ru.otus.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Getter
@Builder(toBuilder = true)
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Table(name = "clients")
public class Client {

    @Id
    @Column("id")
    Long id;

    @Column("name")
    String name;

    @MappedCollection(idColumn = "client_id")
    Address address;

    @MappedCollection(idColumn = "client_id")
    Set<Phone> phones;

    public Client(String name, Address address, Set<Phone> phones) {
        this(null, name, address, phones);
    }

    @PersistenceCreator
    private Client(Long id, String name, Address address, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

}
