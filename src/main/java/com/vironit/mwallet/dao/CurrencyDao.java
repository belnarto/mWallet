package com.vironit.mwallet.dao;

import com.vironit.mwallet.model.entity.Currency;

public interface CurrencyDao extends CrudDao<Currency> {

    Currency findByName(String name);

}
