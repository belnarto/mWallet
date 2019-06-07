package com.vironit.mWallet;

import com.vironit.mWallet.dao.CurrencyDao;
import com.vironit.mWallet.dao.CurrencyDaoJDBC;
import com.vironit.mWallet.utils.DataSource;
import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String PREPARED_SQL_FIND_BY_ID = "SELECT * FROM currency WHERE id = ?;";
    private static final String PREPARED_SQL_FIND_BY_NAME = "SELECT * FROM currency WHERE name = ?;";
    private static final String PREPARED_SQL_FIND_ALL = "SELECT * FROM currency;";
    private static final String PREPARED_SQL_SAVE = "INSERT INTO public.\"currency\" (\"name\",rate) VALUES (?, ?);";
    private static final String PREPARED_SQL_UPDATE = "UPDATE public.\"currency\" SET \"name\" = ?, rate = ? WHERE id = ?;";
    private static final String PREPARED_SQL_DELETE = "DELETE FROM public.\"currency\" WHERE id = ?;";
    //private static final String URL = "jdbc:postgresql://87.252.246.155:58360/postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";

    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "vironit";
    private static final int POOL_SIZE = 20;

    public static void main(String[] args) throws Exception {

        //Jdbc3PoolingDataSource source = new Jdbc3PoolingDataSource();

        //static {
            //source.setUrl(URL);
            //source.setUser(USERNAME);
            //source.setPassword(PASSWORD);
            //source.setMaxConnections(POOL_SIZE);
       // }


    }
}