package com.example.oauth;

public class VerifyPassword {
   public boolean verifyPassword(String enteredPassword, String storedPasswordHash){
       String enteredPasswordHash = PasswordHasher.hashPassword(enteredPassword);
       if(enteredPasswordHash.equals(storedPasswordHash)) {
           return true;
       }
       return false;
   }
}
