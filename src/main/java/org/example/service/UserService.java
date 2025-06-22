package org.example.service;

import org.example.dao.UserDao;
import org.example.model.User;

import java.util.List;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void registerUser(String name, String email, int age) {
        User user = new User(name, email, age, null);
        userDao.create(user);
    }

    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    public User getUserById(int id) {
        return userDao.getById(id);
    }
}