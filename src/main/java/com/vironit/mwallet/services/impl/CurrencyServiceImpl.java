package com.vironit.mwallet.services.impl;

import com.vironit.mwallet.dao.CurrencyDao;
import com.vironit.mwallet.models.dto.CurrencyDto;
import com.vironit.mwallet.models.entity.Currency;
import com.vironit.mwallet.services.CurrencyService;
import com.vironit.mwallet.services.mapper.CurrencyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
@Transactional(value = "jdbcTransactionManager")
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyDao currencyDao;

    @Autowired
    private CurrencyMapper currencyMapper;

    @Override
    public CurrencyDto findById(int id) {
        Currency currency = currencyDao.findById(id);
        return currencyMapper.toDto(currency);
    }

    @Override
    public CurrencyDto findByName(String name) {
        Currency currency = currencyDao.findByName(name);
        return currencyMapper.toDto(currency);
    }

    @Override
    public List<CurrencyDto> findAll() {
        return currencyDao.findAll().stream()
                .map(currency -> currencyMapper.toDto(currency))
                .collect(Collectors.toList());
    }

    @Override
    public void save(CurrencyDto currencyDto) {
        currencyDao.save(currencyMapper.toEntity(currencyDto));
    }

    @Override
    public void delete(CurrencyDto currencyDto) {
        currencyDao.delete(currencyMapper.toEntity(currencyDto));
    }

    @Override
    public void update(CurrencyDto currencyDto) {
        currencyDao.update(currencyMapper.toEntity(currencyDto));
    }

}
