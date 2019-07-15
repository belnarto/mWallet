package com.vironit.mwallet.dao;

import com.vironit.mwallet.models.Currency;

public interface CurrencyDao extends CrudDao<Currency> {

    Currency findByName(String name);

}
