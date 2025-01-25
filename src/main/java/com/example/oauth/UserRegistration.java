package com.example.oauth;
import com.example.oauth.db.DatabaseConnectionPool;
import com.example.oauth.validation.ValidationService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UserRegistration implements UserRegistrationService {

    private ValidationService validationService;
    private DatabaseConnectionPool dbConnectionPool;



    public UserRegistration(ValidationService validationService, DatabaseConnectionPool dbConnectionPool){
        this.validationService = validationService;
        this.dbConnectionPool  = dbConnectionPool;
    }


    @Override
    public boolean registerUser(String username, String email, String password) {
        String query = "INSERT INTO USERS (USERNAME, EMAIL, PASSWORD) VALUES (?,?,?)";
        try(Connection connection = dbConnectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, username);
            statement.setString(2,email);
            statement.setString(3,password);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

    }
}