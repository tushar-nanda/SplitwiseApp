CREATE DATABASE IF NOT EXISTS splitwise_single_group;

USE splitwise_single_group;

CREATE TABLE users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE expenses (
                          expense_id INT AUTO_INCREMENT PRIMARY KEY,
                          description VARCHAR(255) NOT NULL,
                          amount DECIMAL(10, 2) NOT NULL,
                          paid_by INT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (paid_by) REFERENCES users(user_id)
);

CREATE TABLE expense_shares (
                                expense_id INT,
                                user_id INT,
                                share DECIMAL(10, 2) NOT NULL,
                                PRIMARY KEY (expense_id, user_id),
                                FOREIGN KEY (expense_id) REFERENCES expenses(expense_id),
                                FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE balances (
                          balance_id INT AUTO_INCREMENT PRIMARY KEY,
                          debtor_id INT,
                          creditor_id INT,
                          amount DECIMAL(10, 2) NOT NULL,
                          FOREIGN KEY (debtor_id) REFERENCES users(user_id),
                          FOREIGN KEY (creditor_id) REFERENCES users(user_id)
);