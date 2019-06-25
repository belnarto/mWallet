package com.vironit.mWallet.dao.impl;

import com.vironit.mWallet.config.DataSource;
import com.vironit.mWallet.dao.CurrencyDao;
import com.vironit.mWallet.models.Currency;
import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDaoImpl implements CurrencyDao {

    private static final Jdbc3PoolingDataSource SOURCE = DataSource.getInstance().getDataSource();
    private static final String PREPARED_SQL_FIND_BY_ID = "SELECT * FROM currency WHERE id = ?;";
    private static final String PREPARED_SQL_FIND_BY_NAME = "SELECT * FROM currency WHERE name = ?;";
    private static final String PREPARED_SQL_FIND_ALL = "SELECT * FROM currency;";
    private static final String PREPARED_SQL_SAVE = "INSERT INTO public.\"currency\" (\"name\",rate) VALUES (?, ?);";
    private static final String PREPARED_SQL_UPDATE = "UPDATE public.\"currency\" SET \"name\" = ?, rate = ? WHERE id = ?;";
    private static final String PREPARED_SQL_DELETE = "DELETE FROM public.\"currency\" WHERE id = ?;";

    @Override
    public Currency findById(int id) {
        Currency currency = new Currency();
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PREPARED_SQL_FIND_BY_ID)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                currency.setId(id);
                currency.setName(resultSet.getString("name"));
                currency.setRate(Double.valueOf(resultSet.getString("rate")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currency;
    }

    @Override
    public Currency findByName(String name) {
        Currency currency = new Currency();

        try (Connection connection = SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PREPARED_SQL_FIND_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                currency.setName(name);
                currency.setId(Integer.valueOf(resultSet.getString("id")));
                currency.setRate(Double.valueOf(resultSet.getString("rate")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currency;
    }

    @Override
    public List<Currency> findAll() {
        List<Currency> result = new ArrayList<>();
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PREPARED_SQL_FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Currency currency = new Currency();
                currency.setId(Integer.valueOf(resultSet.getString("id")));
                currency.setName(resultSet.getString("name"));
                currency.setRate(Double.valueOf(resultSet.getString("rate")));
                result.add(currency);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void save(Currency currency) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PREPARED_SQL_SAVE)) {
            preparedStatement.setString(1, currency.getName());
            preparedStatement.setDouble(2, currency.getRate());
            preparedStatement.execute();

            // set assigned id to currency object from parameter
            currency.setId(findByName(currency.getName()).getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Currency currency) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PREPARED_SQL_UPDATE)) {
            preparedStatement.setString(1, currency.getName());
            preparedStatement.setDouble(2, currency.getRate());
            preparedStatement.setInt(3, currency.getId());
            preparedStatement.execute();

            // set assigned id to currency object from parameter
            currency.setId(findByName(currency.getName()).getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Currency currency) {
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PREPARED_SQL_DELETE)) {
            preparedStatement.setInt(1, currency.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
