package model;

public class Balance {
    private int balanceId;

    // the one who owes the other person money
    private User debtor;

    // the one who has paid the money to the debtor
    private User creditor;


    private double amount;

    public Balance() {}

    public Balance(User debtor, User creditor, double amount) {
        this.debtor = debtor;
        this.creditor = creditor;
        this.amount = amount;
    }

    public int getBalanceId() { return balanceId; }
    public void setBalanceId(int balanceId) { this.balanceId = balanceId; }
    public User getDebtor() { return debtor; }
    public void setDebtor(User debtor) { this.debtor = debtor; }
    public User getCreditor() { return creditor; }
    public void setCreditor(User creditor) { this.creditor = creditor; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    @Override
    public String toString() {
        return debtor.getName() + " owes " + creditor.getName() + ": ₹" + amount;
    }
}