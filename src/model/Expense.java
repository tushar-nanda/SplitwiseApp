package model;

import java.util.Date;
import java.util.Map;

public class Expense {
    private int expenseId;
    private String description;
    private double amount;
    private User paidBy;
    private Date createdAt;
    private Map<User, Double> shares;

    public Expense() {}

    public Expense(String description, double amount, User paidBy) {
        this.description = description;
        this.amount = amount;
        this.paidBy = paidBy;
    }

    public int getExpenseId() { return expenseId; }
    public void setExpenseId(int expenseId) { this.expenseId = expenseId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public User getPaidBy() { return paidBy; }
    public void setPaidBy(User paidBy) { this.paidBy = paidBy; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Map<User, Double> getShares() { return shares; }
    public void setShares(Map<User, Double> shares) { this.shares = shares; }

    @Override
    public String toString() {
        return "Expense [expenseId=" + expenseId + ", description=" + description
                + ", amount=" + amount + ", paidBy=" + paidBy.getName() + "]";
    }
}