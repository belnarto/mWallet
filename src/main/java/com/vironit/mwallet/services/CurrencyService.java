package com.vironit.mwallet.services;

import com.vironit.mwallet.models.dto.CurrencyDto;

import java.util.List;

public interface CurrencyService {

    CurrencyDto findById(int id);

    CurrencyDto findByName(String name);

    List<CurrencyDto> findAll();

    void save(CurrencyDto currencyDto);

    void delete(CurrencyDto currencyDto);

    void update(CurrencyDto currencyDto);

}
