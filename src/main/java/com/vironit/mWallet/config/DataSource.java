package com.vironit.mWallet.config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

/**
 * Realized in singleton with lazy initialization just for training.<p>
 * Applied double-checked locking & volatile also for training.<p>
 * Because there are no static methods in this class except getInstance()
 * it could be realized in class loading initialization.
 */
public final class DataSource {

    private static volatile DataSource instance;
    private static Jdbc3PoolingDataSource source;

    private DataSource() {
    }

    public static DataSource getInstance() {
        if (instance == null) {
            synchronized (DataSource.class) {
                if (instance == null) {
                    init();
                }
            }
        }
        return instance;
    }

    public Jdbc3PoolingDataSource getDataSource() {
        return source;
    }

    private static void init() {
        instance = new DataSource();
        source = new Jdbc3PoolingDataSource();
        Properties properties = new Properties();
        File file = new File("src/main/resources/db.properties");

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(
                new FileInputStream(file))) {
            properties.load(bufferedInputStream);

            String url = properties.getProperty("URL");
            String username = properties.getProperty("USERNAME");
            String password = properties.getProperty("PASSWORD");
            int poolSize = Integer.valueOf(properties.getProperty("POOL_SIZE"));

            source.setUrl(url);
            source.setUser(username);
            source.setPassword(password);
            source.setMaxConnections(poolSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
