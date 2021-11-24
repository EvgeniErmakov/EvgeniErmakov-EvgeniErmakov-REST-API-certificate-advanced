package com.epam.esm.dao.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {

    @Value("postgres")
    private String name;

    @Value("jdbc:postgresql://localhost:5432/postgres")
    private String url;

    @Value("123")
    private String password;

    @Value("org.postgresql.Driver")
    private String driver;

    @Value("org.hibernate.dialect.PostgreSQLDialect")
    private String dialect;

    @Value("true")
    private String showSql;

    @Value("update")
    private String hbm2DdlAuto;

    @Value("com.epam.esm")
    private String packagesForScan;

    @Bean
    public DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(name);
        config.setPassword(password);
        config.setDriverClassName(driver);
        return new HikariDataSource(config);
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setPackagesToScan(packagesForScan);
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", dialect);
        hibernateProperties.put("hibernate.show_sql", showSql);
        hibernateProperties.put("hibernate.hbm2ddl.auto", hbm2DdlAuto);
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }
}
