package dao;

import models.Currency;

import java.util.List;

public interface CurrencyDao {

    Currency findById(int id);

    Currency findByName(String name);

    void save(Currency currency);

    void update(Currency currency);

    void delete(Currency currency);

    List<Currency> findAll();

}
