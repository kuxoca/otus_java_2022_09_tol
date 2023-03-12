package ru.otus.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Builder
@ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Table(name = "phones")
public class Phone {

    @Id
    @Column("id")
    @ToString.Exclude
    Long id;

    @Column("number")
    String number;
}
