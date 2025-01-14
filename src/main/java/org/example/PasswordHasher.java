package org.example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordHasher {
    public static String hashPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);

        if(encoder.matches(password, hashedPassword)){
            return hashedPassword;
        }
        else return password;
}
}
