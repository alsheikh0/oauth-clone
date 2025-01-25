package com.example.oauth.validation;

@FunctionalInterface
public interface Validator<T> {
    ValidationResult validate(T input);
}
