package com.vironit.mwallet.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrencyDto {

    private int id;
    private String name;
    private double rate;

}
