package org.example;

import java.sql.*;
import java.util.regex.Pattern;


public class UserValidation {


    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private static final String USERNAME_REGEX = "^[A-Za-z0-9_]{3,15}$";

    public static boolean isEmailValid(String email) {

        return email != null && Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean isUsernameValid(String username){
         return username != null && Pattern.matches(USERNAME_REGEX, username);
    }

    public static boolean isEmailUnique(String email){
        return IsValueUnique.isValueUnique("EMAIL", email);
    }
    public static boolean isUsernameUnique(String username){
        return IsValueUnique.isValueUnique("USERNAME", username);
    }



}
