package dao;

import exception.ExcessAmountException;
import model.Balance;
import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BalanceDAO {
    public boolean updateBalance(int debtorId, int creditorId, double amount) {
        String checkSql = "SELECT balance_id, amount FROM balances " +
                "WHERE debtor_id = ? AND creditor_id = ?";
        String updateSql = "UPDATE balances SET amount = ? WHERE balance_id = ?";
        String insertSql = "INSERT INTO balances(debtor_id, creditor_id, amount) VALUES(?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, debtorId);
                checkStmt.setInt(2, creditorId);

                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        int balanceId = rs.getInt("balance_id");
                        double currentAmount = rs.getDouble("amount");

                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setDouble(1, currentAmount + amount);
                            updateStmt.setInt(2, balanceId);
                            return updateStmt.executeUpdate() > 0;
                        }
                    } else {
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                            insertStmt.setInt(1, debtorId);
                            insertStmt.setInt(2, creditorId);
                            insertStmt.setDouble(3, amount);
                            return insertStmt.executeUpdate() > 0;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error updating balance: " + e.getMessage());
        }
        return false;
    }

    public boolean settleBalance(int debtorId, int creditorId, double amount) {
        String sql = "UPDATE balances SET amount = amount - ? " +
                "WHERE debtor_id = ? AND creditor_id = ?";
        String check = "select amount from balances where debtor_id = ? and creditor_id = ?";
        try
        {
             Connection conn = DatabaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(check);
             st.setInt(1, debtorId);
             st.setInt(2, creditorId);

             ResultSet rs = st.executeQuery();
             if(rs.next())
             {
                 double balance = rs.getDouble("amount");
                 if(balance < amount)
                 {
                     throw new ExcessAmountException();
                 }
                 else
                 {
                     balance = balance - amount;
                 }
             }
             PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setDouble(1, amount);
            stmt.setInt(2, debtorId);
            stmt.setInt(3, creditorId);

            return stmt.executeUpdate() > 0;
        }
        catch (ExcessAmountException e)
        {
            System.out.println(e.getMessage());
        }
        catch (SQLException e) {
            System.err.println("Error settling balance: " + e.getMessage());
        }
        return false;
    }

    public List<Balance> getAllBalances() {
        List<Balance> balances = new ArrayList<>();
        String sql = "SELECT b.balance_id, b.amount, " +
                "d.user_id as debtor_id, d.name as debtor_name, " +
                "c.user_id as creditor_id, c.name as creditor_name " +
                "FROM balances b " +
                "JOIN users d ON b.debtor_id = d.user_id " +
                "JOIN users c ON b.creditor_id = c.user_id " +
                "WHERE b.amount > 0";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Balance balance = new Balance();
                balance.setBalanceId(rs.getInt("balance_id"));
                balance.setAmount(rs.getDouble("amount"));

                User debtor = new User();
                debtor.setUserId(rs.getInt("debtor_id"));
                debtor.setName(rs.getString("debtor_name"));

                User creditor = new User();
                creditor.setUserId(rs.getInt("creditor_id"));
                creditor.setName(rs.getString("creditor_name"));

                balance.setDebtor(debtor);
                balance.setCreditor(creditor);

                balances.add(balance);
            }
        } catch (SQLException e) {
            System.err.println("Error getting balances: " + e.getMessage());
        }
        return balances;
    }
}