package com.vironit.mWallet.dao;

import com.vironit.mWallet.models.Currency;

public interface CurrencyDao extends CrudDao<Currency> {

    Currency findByName(String name);

}
