package com.vironit.mwallet.config;

import com.vironit.mwallet.models.Currency;
import com.vironit.mwallet.models.Role;
import com.vironit.mwallet.models.User;
import com.vironit.mwallet.models.Wallet;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.core.env.Environment;

import java.util.Properties;
import javax.sql.DataSource;

import static org.hibernate.cfg.AvailableSettings.*;

@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "ConstantConditions"})
@Configuration
@PropertySource("classpath:db_hibernate.properties")
@EnableTransactionManagement
@ComponentScan(basePackages = "com.vironit.mwallet")
public class PersistenceConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("postgresql.driver"));
        dataSource.setUrl(env.getProperty("postgresql.jdbcUrl"));
        dataSource.setUsername(env.getProperty("postgresql.username"));
        dataSource.setPassword(env.getProperty("postgresql.password"));
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(getDataSource());

        Properties props = new Properties();
        // Setting Hibernate properties
        props.put(SHOW_SQL, env.getProperty("hibernate.show_sql"));
        props.put(HBM2DDL_AUTO, env.getProperty("hibernate.hbm2ddl.auto"));

        // Setting C3P0 properties
        props.put(C3P0_MIN_SIZE, env.getProperty("hibernate.c3p0.min_size"));
        props.put(C3P0_MAX_SIZE, env.getProperty("hibernate.c3p0.max_size"));
        props.put(C3P0_ACQUIRE_INCREMENT, env.getProperty("hibernate.c3p0.acquire_increment"));
        props.put(C3P0_TIMEOUT, env.getProperty("hibernate.c3p0.timeout"));
        props.put(C3P0_MAX_STATEMENTS, env.getProperty("hibernate.c3p0.max_statements"));
        props.put(C3P0_IDLE_TEST_PERIOD, env.getProperty("hibernate.c3p0.idle_test_period"));

        factoryBean.setHibernateProperties(props);
        factoryBean.setAnnotatedClasses(User.class, Wallet.class, Role.class, Currency.class);
        return factoryBean;
    }

    @Bean(name = "hibernateTransactionManager")
    public HibernateTransactionManager getHibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }

    /**
     * Repository for enabling Spring Security Remember Me service.
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(getDataSource());
        return tokenRepository;
    }

    /**
     * JdbcTemplate is the central class to handle JDBC operations.
     * This class executes SQL queries or updates.
     * JdbcTemplate simplifies use of JDBC and avoids common errors.
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    @Bean(name = "jdbcTransactionManager")
    public PlatformTransactionManager getJdbcTransactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }
}
