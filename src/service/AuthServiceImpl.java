package service;

import dao.UserDAO;
import exception.InvalidEmailException;
import model.User;
import java.util.Scanner;

public class AuthServiceImpl implements AuthService {
    private UserDAO userDAO;
    private Scanner scanner;

    public AuthServiceImpl() {
        this.userDAO = new UserDAO();
        this.scanner = new Scanner(System.in);
    }

    public User register() {
        System.out.println("\n--- User Registration ---");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        try
        {
            if (!email.matches(regex)) {
                throw new InvalidEmailException();
            }
        }
        catch (InvalidEmailException e)
        {
            System.out.println(e.getMessage());
            return null;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = new User(name, email, password);
        if (userDAO.addUser(user)) {
            System.out.println("Registration successful!");
            return user;
        } else {
            System.out.println("Registration failed. Email may already exist.");
            return null;
        }
    }

    public User login() {
        System.out.println("\n--- User Login ---");
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userDAO.getUserByEmailAndPassword(email, password);
        if (user != null) {
            System.out.println("Login successful! Welcome, " + user.getName());
            return user;
        } else {
            System.out.println("Invalid email or password.");
            return null;
        }
    }
}