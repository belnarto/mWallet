package com.vironit.mwallet.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Entity
@Table(name = "currency")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PositiveOrZero(message = "Can't be negative")
    private int id;

    @Column(name = "name")
    @NotNull(message = "Can't be null")
    @Size(min = 3, max = 6, message = "Should be bigger than 2 and less than 7")
    private String name;

    @Column(name = "rate")
    @Positive(message = "Can't be negative")
    private double rate;

    @Override
    public String toString() {
        return name;
    }
}
