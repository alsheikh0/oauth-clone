package org.example;

import org.mockito.MockedStatic;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

class UserRegistrationTest {

    void testRegisterUser_Success(){

        try(MockedStatic<UserValidation> mockUserValidation = mockStatic(UserValidation.class);
            MockedStatic<IsValueUnique> mockIsValueUnique = mockStatic(IsValueUnique.class);
            MockedStatic<PasswordHasher> mockPasswordHasher = mockStatic(PasswordHasher.class);
        ){
            String email = "test@example.com";
            String password = "password";
            String username ="testuser";
            String hashedPassword = new BCryptPasswordEncoder().encode(password);

            mockUserValidation.when(() -> {
                UserValidation.isEmailValid(email);
            }).thenReturn(true);
            mockUserValidation.when( () -> {
                UserValidation.isUsernameValid(username);
            }).thenReturn(true);

            mockIsValueUnique.when( () -> {
                IsValueUnique.isValueUnique("email", email);
            }).thenReturn(true);
            mockIsValueUnique.when( () -> {
                IsValueUnique.isValueUnique("username", username);
            }).thenReturn(true);

            mockPasswordHasher.when( () -> {
                PasswordHasher.hashPassword(password);
            }).thenReturn(hashedPassword);

            boolean result = UserRegistration.registerUser(email, username, password);

            assertTrue(result);
        }
    }
}