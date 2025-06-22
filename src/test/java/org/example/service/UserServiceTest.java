package org.example.service;

import org.example.dao.UserDao;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserDao userDao;
    private UserService userService;

    @BeforeEach
    void setup() {
        userDao = mock(UserDao.class);
        userService = new UserService(userDao);
    }

    @Test
    void testRegisterUser() {
        userService.registerUser("Ivan", "Ivan@gmail.com", 25);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao).create(userCaptor.capture());

        User captured = userCaptor.getValue();
        assertEquals("Ivan", captured.getName());
        assertEquals("Ivan@gmail.com", captured.getEmail());
        assertEquals(25, captured.getAge());
    }

    @Test
    void testGetAllUsers() {
        when(userDao.getAll()).thenReturn(Collections.emptyList());
        assertTrue(userService.getAllUsers().isEmpty());
    }

    @Test
    void testGetUserById() {
        User user = new User("Ivan", "Ivan@gmail.com", 25, null);
        user.setId(1);

        when(userDao.getById(1)).thenReturn(user);

        User result = userService.getUserById(1);

        assertEquals(user, result);
        verify(userDao).getById(1);
    }

}
