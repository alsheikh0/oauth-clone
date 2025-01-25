package com.example.oauth.db;

import java.sql.Connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.SQLException;

public class DatabaseConnectionPool {

    private final HikariDataSource dataSource;

    public DatabaseConnectionPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DatabaseConfig.getUrl());
        config.setUsername(DatabaseConfig.getUsername());
        config.setPassword(DatabaseConfig.getPassword());
        config.setMaximumPoolSize(5);
        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        if(dataSource != null) {
            dataSource.close();
        }
    }
}
