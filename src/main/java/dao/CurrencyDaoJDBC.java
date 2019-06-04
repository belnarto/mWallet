package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Currency;
import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

public class CurrencyDaoJDBC implements CurrencyDao {

    private static final String url="jdbc:postgresql://87.252.246.155:58360/postgres";
    private static final String login = "postgres";
    private static final String password = "vironit";
    private String sql = "";
    private static final Jdbc3PoolingDataSource source = new Jdbc3PoolingDataSource();

    static {
        source.setUrl(url);
        source.setUser(login);
        source.setPassword(password);
        source.setMaxConnections(20);
    }

    @Override
    public Currency findById(int id) {
        Currency currency = new Currency();
        sql = "SELECT * FROM currency WHERE id = " + id;
        try(Connection connection = source.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

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
        sql = "SELECT * FROM currency WHERE name = '" + name + "'";
        try(Connection connection = source.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

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
    public void save(Currency currency) {
        sql = "INSERT INTO public.\"currency\" (\"name\",rate)\n" +
                "VALUES ('" + currency.getName() + "', " + currency.getRate() + ") \n" +
                "ON CONFLICT (\"name\")\n" +
                "DO UPDATE SET \"name\" = excluded .\"name\", rate = excluded .rate;";
        try(Connection connection = source.getConnection();
            Statement statement = connection.createStatement()) {
            boolean result = statement.execute(sql);
            currency.setId(findByName(currency.getName()).getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Currency currency) {
        save(currency);
    }

    @Override
    public void delete(Currency currency) {
        sql = "delete from currency where id = " + currency.getId() + ";";
        try(Connection connection = source.getConnection();
            Statement statement = connection.createStatement()) {
            boolean result = statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Currency> findAll() {
        Currency currency = new Currency();
        List<Currency> result = new ArrayList<>();
        sql = "SELECT * FROM currency;" ;
        try(Connection connection = source.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                currency = new Currency();
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

}
