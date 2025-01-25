package com.example.oauth;

import com.example.oauth.db.DatabaseConnectionPool;
import com.example.oauth.validation.ValidationResult;
import com.example.oauth.validation.ValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.mockito.Mockito.*;

public class UserRegistrationTest{
    ValidationService validationServiceMock;
    DatabaseConnectionPool dbPoolMock;
    UserRegistration userRegistration;

    @Before
    public void setUp() {
        validationServiceMock = mock(ValidationService.class);
        dbPoolMock = mock(DatabaseConnectionPool.class);
        userRegistration = new UserRegistration(validationServiceMock, dbPoolMock);
    }

    @Test
    @DisplayName("Test Passed. ")
    public void testUserRegistration() {
        when(validationServiceMock.validateEmail("test@example.com")).thenReturn(ValidationResult.success());
        when(validationServiceMock.validate("email","test@something.com")).thenReturn(ValidationResult.success());
        when(validationServiceMock.validate("username", "testuser1")).thenReturn(ValidationResult.success());
        when(validationServiceMock.validate("username","testuser")).thenReturn(ValidationResult.failure("Invalid Email. "));
        when(validationServiceMock.validate("password", "testpassword")).thenReturn(ValidationResult.success());
        when(validationServiceMock.validate("email", "testpassword@")).thenReturn(ValidationResult.failure("Invalid Email. "));


        userRegistration.registerUser("Testuser1","test@example.com","somehow");

        verify(validationServiceMock, times(1)).validate("email", "test@example.com");
        verify(validationServiceMock, times(1)).validate("username", "Testuser1");
        verify(validationServiceMock, times(1)).validate("password", "testpassword");

        verifyNoMoreInteractions(validationServiceMock);

        
    }

}