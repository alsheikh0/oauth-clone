package org.example;

import java.sql.*;

public class IsValueUnique {
    // this method is a result of refactoring two methods "isEmailUnique" and "isUsernameUnique".
    // the field corresponds to which field we are checking to be unique and the value is its respective value from the user
    private static final String DB_URL  = "jdbc:mysql://localhost:3306/user_info";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "ashraf@143";
    public static boolean isValueUnique(String field, String value){
        String query = String.format("SELECT COUNT(*) FROM USERS WHERE %s = ?", field);
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query)){

            statement.setString(1,value); // the value can be email or username
            ResultSet resultset = statement.executeQuery();

            if(resultset.next()){
                int count = resultset.getInt(1);
                return count == 0;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
