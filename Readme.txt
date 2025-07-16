# 📊 Splity

**Slpity** is a simple expense-sharing Java application modeled on Splitwise. It allows users to log and split expenses, calculate balances, and manage debts in a group—all backed by a MySQL database and structured using layered architecture.

---

## 🧱 Project Structure
SplitwiseApp/
├── src/
│   ├── model/
│   │   ├── User.java
│   │   ├── Expense.java
│   │   └── Balance.java
│   ├── dao/
│   │   ├── DatabaseConnection.java
│   │   ├── UserDAO.java
│   │   ├── ExpenseDAO.java
│   │   └── BalanceDAO.java
│   ├── service/
│   │   ├── AuthService.java
│   │   ├── UserService.java
│   │   ├── ExpenseService.java
│   │   └── BalanceService.java
│   └── MainApp.java
├── lib/
│   └── mysql-connector-java-8.0.23.jar
├── 
└── db.sql


---

## 🛠 Tech Stack

- Java 8+
- JDBC (MySQL Connector/J 8.0.23)
- MySQL Database
- Object-Oriented Design

---

## 🚀 Features

- **User Management:** Register and authenticate users
- **Expense Tracking:** Add and categorize expenses between users
- **Balance Calculation:** Determine who owes whom
- **Modular Design:** Decoupled architecture with clear separation of concerns

---

## 🗄 Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/tushar-nanda/SplitwiseApp.git

- Import into your IDE
- Open the project in IntelliJ, Eclipse, or VS Code (Java extension required).
- Set up MySQL
- Run the schema.sql file inside the database/ directory to create necessary tables.
- Configure DB Credentials
- Update your MySQL username/password in DatabaseConnection.java.
- Add External Library
- Ensure mysql-connector-java-8.0.23.jar is added to your classpath.
- Run the Application
- Start with MainApp.java. Follow on-screen prompts to interact.

🤝 Contributions Contributions are welcome! Feel free to fork the project, raise issues, or submit pull requests.

📬 ContactMaintained by Tushar , Aniket ,Sachin. If you'd like to collaborate or have suggestions, feel free to reach out!


Built with 💻, ☕, and a pinch of teamwork.
If you plan to make the repo public or use it for placement purposes, I can also help you write a project description or summary that you could include in your portfolio or resume. Let me know if you'd like that.


If you plan to make the repo public or use it for placement purposes, I can also help you write a project description or summary that you could include in your portfolio or resume. Let me know if you'd like that.