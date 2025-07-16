import service.*;
import model.User;
import java.util.Scanner;

public class MainApp {
    private static User currentUser;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        authLoop: while (currentUser == null) {
            System.out.println("\n=== Splitwise App ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            int authChoice = Integer.parseInt(scanner.nextLine());

            AuthServiceImpl authService = new AuthServiceImpl();
            switch (authChoice) {
                case 1:
                    authService.register();
                    break;
                case 2:
                    currentUser = authService.login();
                    if (currentUser != null) {
                        break authLoop;
                    }
                    break;
                case 0:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        while (running) {
            System.out.println("\n=== Splitwise App ===");
            System.out.println("Logged in as: " + currentUser.getName());
            System.out.println("1. List All Users");
            System.out.println("2. Add Expense");
            System.out.println("3. View All Expenses");
            System.out.println("4. View Balances");
            System.out.println("5. Settle Balance");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    listAllUsers();
                    break;
                case 2:
                    addExpense();
                    break;
                case 3:
                    listAllExpenses();
                    break;
                case 4:
                    viewBalances();
                    break;
                case 5:
                    settleBalance();
                    break;
                case 0:
                    currentUser = null;
                    System.out.println("Logged out successfully.");
                    main(args);
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void listAllUsers() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.listAllUsers();
    }

    private static void addExpense() {
        ExpenseServiceImpl expenseService = new ExpenseServiceImpl();
        expenseService.addExpense(currentUser);
    }

    private static void listAllExpenses() {
        ExpenseServiceImpl expenseService = new ExpenseServiceImpl();
        expenseService.listAllExpenses();
    }

    private static void viewBalances() {
        BalanceServiceImpl balanceService = new BalanceServiceImpl();
        balanceService.showAllBalances();
    }

    private static void settleBalance() {
        BalanceServiceImpl balanceService = new BalanceServiceImpl();
        balanceService.settleBalance();
    }
}