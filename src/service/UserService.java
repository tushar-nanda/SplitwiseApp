package service;

import model.User;

import java.util.List;

public interface UserService {
    public void listAllUsers();
    public User getUserById(int userId);
    public List<User> getAllUsers();
}
