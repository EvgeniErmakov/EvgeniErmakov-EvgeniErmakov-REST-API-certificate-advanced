package com.epam.esm.dao.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties
public class DatabaseConfig {

    @Autowired
    private DbProperties dbProperties;

    @Bean
    public DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbProperties.getUrl());
        config.setUsername(dbProperties.getUsername());
        config.setPassword(dbProperties.getPassword());
        config.setDriverClassName(dbProperties.getDriverClassName());
        return new HikariDataSource(config);
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setPackagesToScan(dbProperties.getPackagesForScan());
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", dbProperties.getDialect());
        hibernateProperties.put("hibernate.show_sql", dbProperties.getShowSql());
        hibernateProperties.put("hibernate.hbm2ddl.auto", dbProperties.getHbm2DdlAuto());
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }
}
