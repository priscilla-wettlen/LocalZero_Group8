package app;

import server.model.User;
import server.service.AccountService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LocalZeroAuthDemo {
    public static void main(String[] args) {
        AccountService accountService = AccountService.getAccountServiceInstance();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Choose an option:");
            System.out.println("1) Register");
            System.out.println("2) Login");
            System.out.print("Enter 1 or 2: ");
            String choice = reader.readLine();

            if ("1".equals(choice)) {
                System.out.print("Register email: ");
                String registerEmail = reader.readLine();
                System.out.print("Register password: ");
                String registerPassword = reader.readLine();

                User registered = accountService.register(registerEmail, registerPassword);
                if (registered == null) {
                    System.out.println("User already exists or invalid input.");
                } else {
                    System.out.println("Registered user: " + registered.getEmail());
                }
            } else if ("2".equals(choice)) {
                System.out.print("Login email: ");
                String loginEmail = reader.readLine();
                System.out.print("Login password: ");
                String loginPassword = reader.readLine();

                String userId = accountService.login(loginEmail, loginPassword);
                if (userId.isEmpty()) {
                    System.out.println("Login failed.");
                } else {
                    System.out.println("Login success. userId=" + userId);
                }
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (IOException e) {
            System.out.println("Failed to read input.");
        }
    }
}
