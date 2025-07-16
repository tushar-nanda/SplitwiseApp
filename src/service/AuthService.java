package service;

import model.User;

public interface AuthService {
    public User register();
    public User login();

    }
