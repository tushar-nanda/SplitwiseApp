package service;

import dao.UserDAO;
import model.User;
import java.util.List;

public class UserServiceImpl implements UserService{
    private UserDAO userDAO;

    public UserServiceImpl() {
        this.userDAO = new UserDAO();
    }

    public void listAllUsers() {
        List<User> users = userDAO.getAllUsers();
        System.out.println("\n--- All Users ---");
        for (User user : users) {
            System.out.println("ID: " + user.getUserId() + ", Name: " + user.getName() +
                    ", Email: " + user.getEmail());
        }
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }
}