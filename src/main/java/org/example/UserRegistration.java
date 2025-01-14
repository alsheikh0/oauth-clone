package org.example;

import java.sql.*;
import java.util.Random;

public class UserRegistration {
    private static final String DB_URL  = "jdbc:mysql://localhost:3306/user_info";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "ashraf@143";


    public static boolean registerUser(String email, String username, String password) {
        if (!UserValidation.isEmailValid(email)) {
            System.out.println("Invalid email");
            return false;
        }
        if (!UserValidation.isUsernameValid(username)) {
            System.out.println("Invalid username");
            return false;
        }
        if (!IsValueUnique.isValueUnique("email", email)) {
            System.out.println("Email is already registered");
            return false;
        }
        if (!IsValueUnique.isValueUnique("username", username)) {
            System.out.println("Username is already taken");
            return false;
        }

        try {
            String passwordHash = PasswordHasher.hashPassword(password);
            String query = "INSERT INTO USERS (username, email, password) VALUES (?, ?, ?)";
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, username);
                statement.setString(2, email);
                statement.setString(3, passwordHash);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User registered successfully");
                } else {
                    System.out.println("User not registered");
                }
            } // try-with-resources closes the statement and connection automatically
        } catch (SQLException e) {
            e.printStackTrace(); // Or log the error
        }
        return true;
    }
}