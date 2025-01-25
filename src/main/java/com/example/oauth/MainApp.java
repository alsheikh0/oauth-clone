package com.example.oauth;

import com.example.oauth.db.DatabaseConnectionPool;
import com.example.oauth.validation.ValidationService;
import com.example.oauth.db.IsValueUnique;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        DatabaseConnectionPool dbPool = new DatabaseConnectionPool();
        ValidationService validationService = new ValidationService(new IsValueUnique(dbPool));
        VerifyPassword verifyPassword = new VerifyPassword();

        UserRegistration userRegistration = new UserRegistration(validationService ,dbPool);
        UserLogin userLogin = new UserLogin(dbPool, validationService, verifyPassword );

        System.out.println("Welcome to the OAuth App");
        System.out.println("1. Register ");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        int choice = scan.nextInt();
        switch (choice) {
            case  1 : System.out.println("Please enter your username, email and password");
                     if ( userRegistration.registerUser(scan.next(), scan.next(), scan.next())) {
                         System.out.println("You have successfully registered!");
                     }
                     else System.out.println("Sorry, you have not successfully registered!");
                      break;
            case  2 : System.out.println("please enter your username and password");
                      if (userLogin.loginUser(scan.next(), scan.next())) {
                          System.out.println("You have successfully logged in!");
                      }
                      else System.out.println("Sorry, you have not successfully logged in!");

                      break;
            case  3 : System.exit(0);
                      break;
            default:
                throw new IllegalStateException("Unexpected value: " + choice);
        }

        scan.close();


    }
}