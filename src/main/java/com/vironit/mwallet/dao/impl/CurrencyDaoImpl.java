package com.vironit.mwallet.dao.impl;

import com.vironit.mwallet.dao.CurrencyDao;
import com.vironit.mwallet.models.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Repository
public class CurrencyDaoImpl implements CurrencyDao {

    @Autowired
    private Validator validator;

    @Autowired
    DataSource dataSource;

    private static final String PREPARED_SQL_FIND_BY_ID = "SELECT * FROM currency WHERE id = ?;";
    private static final String PREPARED_SQL_FIND_BY_NAME = "SELECT * FROM currency WHERE name = ?;";
    private static final String PREPARED_SQL_FIND_ALL = "SELECT * FROM currency;";
    private static final String PREPARED_SQL_SAVE = "INSERT INTO public.\"currency\" (\"name\",rate) VALUES (?, ?);";
    private static final String PREPARED_SQL_UPDATE = "UPDATE public.\"currency\" SET \"name\" = ?, rate = ? WHERE id = ?;";
    private static final String PREPARED_SQL_DELETE = "DELETE FROM public.\"currency\" WHERE id = ?;";

    @Override
    public void save(Currency currency) {

        Set<ConstraintViolation<Object>> violations = validator.validate(currency);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(
                    new HashSet<ConstraintViolation<?>>(violations));
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PREPARED_SQL_SAVE)) {
            preparedStatement.setString(1, currency.getName());
            preparedStatement.setDouble(2, currency.getRate());
            preparedStatement.execute();

            // set assigned id to currency object from parameter
            currency.setId(findByName(currency.getName()).getId());
        } catch (SQLException ignored) {
        }
    }

    @Override
    public void update(Currency currency) {

        Set<ConstraintViolation<Object>> violations = validator.validate(currency);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(
                    new HashSet<ConstraintViolation<?>>(violations));
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PREPARED_SQL_UPDATE)) {
            preparedStatement.setString(1, currency.getName());
            preparedStatement.setDouble(2, currency.getRate());
            preparedStatement.setInt(3, currency.getId());
            preparedStatement.execute();

            // set assigned id to currency object from parameter
            currency.setId(findByName(currency.getName()).getId());
        } catch (SQLException ignored) {
        }
    }

    @Override
    public void delete(Currency currency) {

        Set<ConstraintViolation<Object>> violations = validator.validate(currency);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(
                    new HashSet<ConstraintViolation<?>>(violations));
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PREPARED_SQL_DELETE)) {
            preparedStatement.setInt(1, currency.getId());
            preparedStatement.execute();
        } catch (SQLException ignored) {
        }
    }

    @Override
    public List<Currency> findAll() {
        List<Currency> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PREPARED_SQL_FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Currency currency = new Currency();
                currency.setId(Integer.valueOf(resultSet.getString("id")));
                currency.setName(resultSet.getString("name"));
                currency.setRate(Double.valueOf(resultSet.getString("rate")));
                result.add(currency);
            }
        } catch (SQLException ignored) {
        }
        return result;
    }

    @Override
    public Currency findById(int id) {
        Currency currency = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PREPARED_SQL_FIND_BY_ID)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                currency = new Currency();
                currency.setId(id);
                currency.setName(resultSet.getString("name"));
                currency.setRate(Double.valueOf(resultSet.getString("rate")));
            }

        } catch (SQLException ignored) {
        }
        return currency;
    }

    @Override
    public Currency findByName(String name) {
        Currency currency = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PREPARED_SQL_FIND_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                currency = new Currency();
                currency.setName(name);
                currency.setId(Integer.valueOf(resultSet.getString("id")));
                currency.setRate(Double.valueOf(resultSet.getString("rate")));
            }
        } catch (SQLException ignored) {
        }
        return currency;
    }
}
