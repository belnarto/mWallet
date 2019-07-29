package com.vironit.mwallet.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrencyDto {

    @PositiveOrZero(message = "Can't be negative")
    private int id;

    @NotNull(message = "Can't be null")
    @Size(min = 3, max = 6, message = "Should be bigger than 2 and less than 7")
    private String name;

    @Positive(message = "Can't be negative")
    private double rate;

    @Override
    public String toString() {
        return name;
    }

}
