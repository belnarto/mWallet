package com.vironit.mwallet.config;

import com.vironit.mwallet.config.exception.PersistenceConfigurationException;
import com.vironit.mwallet.model.entity.Currency;
import com.vironit.mwallet.model.entity.MoneyTransferTransaction;
import com.vironit.mwallet.model.entity.PaymentTransaction;
import com.vironit.mwallet.model.entity.RechargeTransaction;
import com.vironit.mwallet.model.entity.Role;
import com.vironit.mwallet.model.entity.Transaction;
import com.vironit.mwallet.model.entity.User;
import com.vironit.mwallet.model.entity.Wallet;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;
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

import java.util.Objects;
import java.util.Properties;
import javax.sql.DataSource;

import static org.hibernate.cfg.AvailableSettings.*;

@Log4j2
@Configuration
@PropertySource("classpath:persistence.properties")
@EnableTransactionManagement
@ComponentScan(basePackages = "com.vironit.mwallet")
public class PersistenceConfig {

    private Environment env;

    public PersistenceConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource getDataSource() throws PersistenceConfigurationException {
        HikariConfig config = new HikariConfig();
        try {
            config.setDriverClassName(env.getProperty("postgresql.driver"));
            config.setJdbcUrl(env.getProperty("postgresql.jdbcUrl"));
            config.setUsername(env.getProperty("postgresql.username"));
            config.setPassword(env.getProperty("postgresql.password"));
            config.setMaximumPoolSize(
                    Integer.parseInt(Objects.requireNonNull(env.getProperty("postgresql.maximumPoolSize"))));
            config.addDataSourceProperty("cachePrepStmts",
                    env.getProperty("postgresql.cachePrepStmts"));
            config.addDataSourceProperty("prepStmtCacheSize",
                    env.getProperty("postgresql.prepStmtCacheSize"));
            config.addDataSourceProperty("prepStmtCacheSqlLimit",
                    env.getProperty("postgresql.prepStmtCacheSqlLimit"));
        } catch (Exception e) {
            log.error("DataSource configuration error.", e);
            throw new PersistenceConfigurationException("DataSource configuration error.", e);
        }
        return new HikariDataSource(config);
    }

    @Bean
    public LocalSessionFactoryBean getSessionFactory() throws PersistenceConfigurationException {
        try {
            LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
            factoryBean.setDataSource(getDataSource());
            Properties props = new Properties();
            props.put(SHOW_SQL, Objects.requireNonNull(env.getProperty("hibernate.show_sql")));
            props.put(FORMAT_SQL, Objects.requireNonNull(env.getProperty("hibernate.format_sql")));
            props.put(USE_SQL_COMMENTS, Objects.requireNonNull(env.getProperty("hibernate.use_sql_comments")));
            props.put(HBM2DDL_AUTO, Objects.requireNonNull(env.getProperty("hibernate.hbm2ddl.auto")));
            factoryBean.setHibernateProperties(props);
            factoryBean.setAnnotatedClasses(User.class,
                    Wallet.class,
                    Role.class,
                    Currency.class,
                    Transaction.class,
                    RechargeTransaction.class,
                    PaymentTransaction.class,
                    MoneyTransferTransaction.class);
            return factoryBean;
        } catch (Exception e) {
            log.error("Hibernate session factory error.", e);
            throw new PersistenceConfigurationException("Hibernate session factory error.", e);
        }
    }

    @Bean(name = "hibernateTransactionManager")
    public HibernateTransactionManager getHibernateTransactionManager() throws PersistenceConfigurationException {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        try {
            transactionManager.setSessionFactory(getSessionFactory().getObject());
        } catch (Exception e) {
            log.error("Hibernate Transaction Manager error.", e);
            throw new PersistenceConfigurationException("Hibernate Transaction Manager error.", e);
        }
        return transactionManager;
    }

    /**
     * JdbcTemplate is the central class to handle JDBC operations.
     * This class executes SQL queries or updates.
     * JdbcTemplate simplifies use of JDBC and avoids common errors.
     */
    @Bean
    public JdbcTemplate jdbcTemplate() throws PersistenceConfigurationException {
        try {
            return new JdbcTemplate(getDataSource());
        } catch (Exception e) {
            log.error("Jdbc template error.", e);
            throw new PersistenceConfigurationException("Jdbc template error.", e);
        }
    }

    @Bean(name = "jdbcTransactionManager")
    public PlatformTransactionManager getJdbcTransactionManager() throws PersistenceConfigurationException {
        try {
            return new DataSourceTransactionManager(getDataSource());
        } catch (Exception e) {
            log.error("Jdbc Transaction Manager error.", e);
            throw new PersistenceConfigurationException("Jdbc Transaction Manager error.", e);
        }
    }

    /**
     * Repository for enabling Spring Security Remember Me service.
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() throws PersistenceConfigurationException {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        try {
            tokenRepository.setDataSource(getDataSource());
        } catch (Exception e) {
            log.error("Persistent Token Repository error.", e);
            throw new PersistenceConfigurationException("Persistent Token Repository error.", e);
        }
        return tokenRepository;
    }

}
