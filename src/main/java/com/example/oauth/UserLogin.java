package com.example.oauth;
import com.example.oauth.validation.ValidationService;

import com.example.oauth.UserLoginService;
import com.example.oauth.db.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLogin implements UserLoginService {
    private final DatabaseConnectionPool dbConnectionPool;
    private ValidationService validationService;
    private VerifyPassword verifyPassword;

    public UserLogin(DatabaseConnectionPool dbConnectionPool, ValidationService validationService, VerifyPassword verifyPassword) {
        this.dbConnectionPool = dbConnectionPool;
        this.validationService = validationService;
        this.verifyPassword = verifyPassword;
    }

    @Override
    public boolean loginUser(String username, String password) {

        if(username == null || username.isBlank() || password == null || password.isBlank()){
            throw new IllegalArgumentException("username or password cannot be null");
        }

        String query = "SELECT PASSWORD_HASH FROM USERS WHERE USERNAME = ?";
        try(Connection connection = dbConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);){

            statement.setString(1,username);
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    String storedPasswordHash = resultSet.getString("PASSWORD_HASH");
                    return verifyPassword.verifyPassword(password, storedPasswordHash);
                }
                return false;
                }

            } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}