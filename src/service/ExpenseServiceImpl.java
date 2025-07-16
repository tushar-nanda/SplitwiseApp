package service;

import dao.BalanceDAO;
import dao.ExpenseDAO;
import dao.UserDAO;
import model.Expense;
import model.User;
import java.util.List;
import java.util.Scanner;

public class ExpenseServiceImpl implements ExpenseService{
    private ExpenseDAO expenseDAO;
    private UserDAO userDAO;
    private Scanner scanner;

    public ExpenseServiceImpl() {
        this.expenseDAO = new ExpenseDAO();
        this.userDAO = new UserDAO();
        this.scanner = new Scanner(System.in);
    }

    public void addExpense(User currentUser) {
        List<User> users = userDAO.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("No users available. Please add users first.");
            return;
        }

        System.out.println("\n--- Add New Expense ---");
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        User paidBy = currentUser;
        System.out.println("Paying for this expense: " + paidBy.getName());

        System.out.println("\nSelect participants (comma-separated numbers, e.g., 1,2,3):");
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i+1) + ". " + users.get(i).getName());
        }
        System.out.print("Enter choices (include yourself if participating): ");
        String[] choices = scanner.nextLine().split(",");

        Expense expense = new Expense(description, amount, paidBy);
        if (expenseDAO.addExpense(expense)) {
            double sharePerPerson = amount / choices.length;

            for (String choice : choices) {
                int userIndex = Integer.parseInt(choice.trim()) - 1;
                User participant = users.get(userIndex);

                if (participant.getUserId() != paidBy.getUserId()) {
                    expenseDAO.addExpenseShare(expense.getExpenseId(),
                            participant.getUserId(),
                            sharePerPerson);
                }
            }

            updateBalances(expense, choices, paidBy, sharePerPerson, users);

            System.out.println("Expense added successfully!");
        } else {
            System.out.println("Failed to add expense.");
        }
    }

    private void updateBalances(Expense expense, String[] choices, User paidBy,
                                double sharePerPerson, List<User> users) {
        BalanceDAO balanceDAO = new BalanceDAO();

        for (String choice : choices) {
            int userIndex = Integer.parseInt(choice.trim()) - 1;
            User participant = users.get(userIndex);

            if (participant.getUserId() != paidBy.getUserId()) {
                balanceDAO.updateBalance(participant.getUserId(),
                        paidBy.getUserId(),
                        sharePerPerson);
            }
        }
    }

    public void listAllExpenses() {
        List<Expense> expenses = expenseDAO.getAllExpenses();
        System.out.println("\n--- All Expenses ---");
        for (Expense expense : expenses) {
            System.out.println(expense.getDescription() + ": ₹" + expense.getAmount() +
                    " paid by " + expense.getPaidBy().getName());

            List<Integer> participantIds = expenseDAO.getUsersInExpense(expense.getExpenseId());
            System.out.print("  Shared by: ");
            for (int i = 0; i < participantIds.size(); i++) {
                User user = userDAO.getUserById(participantIds.get(i));
                double share = expenseDAO.getShareForUserInExpense(expense.getExpenseId(),
                        user.getUserId());
                System.out.print(user.getName() + "(₹" + share + ")");
                if (i < participantIds.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }
}