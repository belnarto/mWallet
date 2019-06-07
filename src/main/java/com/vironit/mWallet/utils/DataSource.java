package com.vironit.mWallet.utils;

import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

/**
 * Realized in singleton with lazy initialization just for training.<p>
 * Applied double-checked locking & volatile also for training.<p>
 * Because there are no static methods in this class except getInstance()
 * it could be realized in class loading initialization.
 */
public final class DataSource {

    private static volatile DataSource instance;
    private static volatile Jdbc3PoolingDataSource source;
    //private static final String URL = "jdbc:postgresql://87.252.246.155:58360/postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "vironit";
    private static final int POOL_SIZE = 20;

    private DataSource() {
    }

    public static DataSource getInstance() {
        if (instance == null) {
            synchronized (DataSource.class) {
                if (instance == null) {
                    instance = new DataSource();
                    source = new Jdbc3PoolingDataSource();
                    source.setUrl(URL);
                    source.setUser(USERNAME);
                    source.setPassword(PASSWORD);
                    source.setMaxConnections(POOL_SIZE);
                }
            }
        }
        return instance;
    }

    public Jdbc3PoolingDataSource getDataSource() {
        return source;
    }

}
