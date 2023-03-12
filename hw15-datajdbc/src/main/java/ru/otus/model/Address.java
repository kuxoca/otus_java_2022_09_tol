package ru.otus.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@AllArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Table(name = "addresses")
public class Address {

    @Id
    @Column("id")
    @ToString.Exclude
    Long id;

    @Column("street")
    String street;


}
