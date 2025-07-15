package dao;

import model.Expense;
import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {
    public boolean addExpense(Expense expense) {
        String sql = "INSERT INTO expenses(description, amount, paid_by) VALUES(?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, expense.getDescription());
            stmt.setDouble(2, expense.getAmount());
            stmt.setInt(3, expense.getPaidBy().getUserId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        expense.setExpenseId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding expense: " + e.getMessage());
        }
        return false;
    }

    public boolean addExpenseShare(int expenseId, int userId, double share) {
        String sql = "INSERT INTO expense_shares(expense_id, user_id, share) VALUES(?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, expenseId);
            stmt.setInt(2, userId);
            stmt.setDouble(3, share);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding expense share: " + e.getMessage());
        }
        return false;
    }

    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT e.expense_id, e.description, e.amount, e.paid_by, e.created_at, " +
                "u.user_id, u.name, u.email " +
                "FROM expenses e " +
                "JOIN users u ON e.paid_by = u.user_id " +
                "ORDER BY e.created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Expense expense = new Expense();
                expense.setExpenseId(rs.getInt("expense_id"));
                expense.setDescription(rs.getString("description"));
                expense.setAmount(rs.getDouble("amount"));

                User paidBy = new User();
                paidBy.setUserId(rs.getInt("user_id"));
                paidBy.setName(rs.getString("name"));
                paidBy.setEmail(rs.getString("email"));
                expense.setPaidBy(paidBy);

                expense.setCreatedAt(rs.getTimestamp("created_at"));
                expenses.add(expense);
            }
        } catch (SQLException e) {
            System.err.println("Error getting expenses: " + e.getMessage());
        }
        return expenses;
    }

    public List<Integer> getUsersInExpense(int expenseId) {
        List<Integer> userIds = new ArrayList<>();
        String sql = "SELECT user_id FROM expense_shares WHERE expense_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, expenseId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    userIds.add(rs.getInt("user_id"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting users in expense: " + e.getMessage());
        }
        return userIds;
    }

    public double getShareForUserInExpense(int expenseId, int userId) {
        String sql = "SELECT share FROM expense_shares WHERE expense_id = ? AND user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, expenseId);
            stmt.setInt(2, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("share");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting share for user: " + e.getMessage());
        }
        return 0.0;
    }
}