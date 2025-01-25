package com.example.oauth.validation;

import com.example.oauth.PasswordHasher;

import com.example.oauth.db.IsValueUnique;

import java.util.Map;

public class ValidationService {

    private final IsValueUnique isValueUnique;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String USERNAME_REGEX = "^[A-Za-z0-9_]{3,15}$";

    private final Map<String, Validator<String>> validators;

    public ValidationService(IsValueUnique isValueUnique) {
        this.isValueUnique = isValueUnique;
        this.validators = Map.of(
                "username", this::validateUsername,
                "email", this::validateEmail,
                "password", this::validatePassword

                // understand this afterward by asking chatgpt

        );
    }

    public ValidationResult validate(String fieldName, String input) {
        Validator<String> validator = validators.get(fieldName);

        if(validator == null) {
            return ValidationResult.failure("No validator found for field" + fieldName);
        }
        return validator.validate(input);
    }

    private ValidationResult validateUsername(String username) {
        if(username == null || username.isBlank()){
            return ValidationResult.failure("Username cannot be null or blank");
        }
        if(!username.matches(USERNAME_REGEX)) {
            return ValidationResult.failure("Invalid Username");
        }

        if(username.length() < 8) {
            return ValidationResult.failure("Username must be at least 8 characters");
        }

        return ValidationResult.success();
    }

    public ValidationResult validateEmail(String email){
        if(email == null || email.isBlank()) {
            return ValidationResult.failure("Email cannot be null or blank");
        }

        if(!email.matches(EMAIL_REGEX)) {
            return ValidationResult.failure("Invalid Email. ");
        }

        if(!isValueUnique.isValueUnique(email)) {
            return ValidationResult.failure("email already registered.");
        }
        return ValidationResult.success();
    }

    private ValidationResult validatePassword(String input) {
        if(input == null || input.isBlank()) {
            return ValidationResult.failure("Password cannot be null or blank.");
        }

        if(input.length() < 8) {
            return ValidationResult.failure("Password must be at least 8 characters.");
        }
        return ValidationResult.success();
    }
    // the below method hashes the entered password and compares it with hashed password stored along with given username
    private ValidationResult verifyPassword(String enteredPassword, String storedPasswordHash) {
        String enteredPasswordHash = PasswordHasher.hashPassword(enteredPassword);
        if(enteredPasswordHash.equals(storedPasswordHash)) {
            return ValidationResult.success();
        }
        return ValidationResult.failure("Passwords do not match");
    }
}