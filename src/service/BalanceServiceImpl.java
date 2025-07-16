package service;

import dao.BalanceDAO;
import dao.UserDAO;
import model.Balance;
import java.util.List;
import java.util.Scanner;

public class BalanceServiceImpl implements BalanceService {
    private BalanceDAO balanceDAO;
    private UserDAO userDAO;
    private Scanner scanner;

    public BalanceServiceImpl() {
        this.balanceDAO = new BalanceDAO();
        this.userDAO = new UserDAO();
        this.scanner = new Scanner(System.in);
    }

    public void showAllBalances() {
        List<Balance> balances = balanceDAO.getAllBalances();
        System.out.println("\n--- All Balances ---");

        if (balances.isEmpty()) {
            System.out.println("No balances to show. All settled up!");
            return;
        }

        for (Balance balance : balances) {
            System.out.println(balance.getDebtor().getName() + " owes " +
                    balance.getCreditor().getName() + ": ₹" + balance.getAmount());
        }
    }

    public void settleBalance() {
        showAllBalances();

        System.out.println("\n--- Settle Balance ---");
        System.out.print("Enter debtor ID: ");
        int debtorId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter creditor ID: ");
        int creditorId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter amount to settle: ");
        double amount = Double.parseDouble(scanner.nextLine());

        if (balanceDAO.settleBalance(debtorId, creditorId, amount)) {
            System.out.println("Balance settled successfully!");
        } else {
            System.out.println("Failed to settle balance.");
        }
    }
}