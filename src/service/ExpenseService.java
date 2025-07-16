package service;

import model.User;

public interface ExpenseService {
    public void addExpense(User currentUser);
    public void listAllExpenses();

}
