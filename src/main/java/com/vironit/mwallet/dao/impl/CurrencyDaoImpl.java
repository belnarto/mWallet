package com.vironit.mwallet.dao.impl;

import com.vironit.mwallet.dao.CurrencyDao;
import com.vironit.mwallet.models.entity.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Repository
public class CurrencyDaoImpl implements CurrencyDao {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Spring JDBC provides BeanPropertyRowMapper that implements RowMapper.
    // We can directly use it in place of custom RowMapper.
    // We use BeanPropertyRowMapper in the scenario when database table
    // column names and our class fields name are of same.
    private RowMapper<Currency> rowMapper = new BeanPropertyRowMapper<>(Currency.class);

    private static final String PREPARED_SQL_FIND_BY_ID = "SELECT * FROM currency WHERE id = ?;";
    private static final String PREPARED_SQL_FIND_BY_NAME = "SELECT * FROM currency WHERE name = ?;";
    private static final String PREPARED_SQL_FIND_ALL = "SELECT * FROM currency;";
    private static final String PREPARED_SQL_SAVE = "INSERT INTO public.\"currency\" (\"name\",rate) VALUES (?, ?);";
    private static final String PREPARED_SQL_UPDATE = "UPDATE public.\"currency\" SET \"name\" = ?, rate = ? WHERE id = ?;";
    private static final String PREPARED_SQL_DELETE = "DELETE FROM public.\"currency\" WHERE id = ?;";

    @Override
    public void save(Currency currency) {
        jdbcTemplate.update(PREPARED_SQL_SAVE, currency.getName(), currency.getRate());
        int id = findByName(currency.getName()).getId();
        currency.setId(id);
    }

    @Override
    public void update(Currency currency) {
        jdbcTemplate.update(PREPARED_SQL_UPDATE, currency.getName(), currency.getRate(), currency.getId());
    }

    @Override
    public void delete(Currency currency) {
        jdbcTemplate.update(PREPARED_SQL_DELETE, currency.getId());
    }

    @Override
    public List<Currency> findAll() {
        return this.jdbcTemplate.query(PREPARED_SQL_FIND_ALL, rowMapper);
    }

    @Override
    public Currency findById(int id) {
        Currency currency;
        try {
            currency = jdbcTemplate.queryForObject(PREPARED_SQL_FIND_BY_ID, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            currency = null;
        }
        return currency;
    }

    @Override
    public Currency findByName(String name) {
        Currency currency;
        try {
            currency = jdbcTemplate.queryForObject(PREPARED_SQL_FIND_BY_NAME, rowMapper, name);
        } catch (EmptyResultDataAccessException e) {
            currency = null;
        }
        return currency;
    }
}
