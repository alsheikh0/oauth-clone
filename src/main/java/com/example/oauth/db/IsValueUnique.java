package com.example.oauth.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IsValueUnique{

    private final DatabaseConnectionPool connectionPool;

    public IsValueUnique(DatabaseConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public boolean isValueUnique(String value) {
        if(!isValidField(value)){
            throw new IllegalArgumentException("Invalid field name " + value);
        }
        String query = "SELECT COUNT(*) FROM USERS WHERE "+value+" =  ? ";

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ){
            statement.setString(1,value);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                return result.getInt(1) == 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking uniqueness for field: " + value);

        }
        return false;

    }

    private boolean isValidField(String field){
        return field.equalsIgnoreCase("username") || field.equalsIgnoreCase("email");
    }


}