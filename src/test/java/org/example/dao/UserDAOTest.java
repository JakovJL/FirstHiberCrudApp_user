package org.example.dao;

import org.example.model.User;
import org.example.utils.HibernateSessionFactoryUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDAOTest {

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    private UserDAOImpl userDAO;

    @BeforeAll
    void setUp() {
        postgres.start();

        System.setProperty("DB_URL", postgres.getJdbcUrl());
        System.setProperty("DB_USERNAME", postgres.getUsername());
        System.setProperty("DB_PASSWORD", postgres.getPassword());

        // Пересоздаём фабрику на новых свойствах
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        userDAO = new UserDAOImpl();
    }

    @AfterAll
    void tearDown() {
        postgres.stop();
    }

    @Test
    @Order(1)
    void testCreate() {
        User user = new User("Alice", "alice@mail.com", 30, OffsetDateTime.now());
        userDAO.create(user);
        assertTrue(user.getId() > 0);

        User retrieved = userDAO.getById(user.getId());
        assertEquals("Alice", retrieved.getName());
        assertEquals("alice@mail.com", retrieved.getEmail());
        assertEquals(30, retrieved.getAge());

    }

    @Test
    @Order(2)
    void testGetAll() {
        List<User> users = userDAO.getAll();
        assertFalse(users.isEmpty());
    }

    @Test
    @Order(3)
    void testUpdate() {
        User user = userDAO.getAll().get(0);
        user.setAge(99);
        userDAO.update(user);

        User updated = userDAO.getById(user.getId());
        assertEquals(99, updated.getAge());
    }

    @Test
    @Order(4)
    void testDelete() {
        User user = new User("NeedToBeDeleted", "Delete@.com", 50, OffsetDateTime.now());
        userDAO.create(user);
        int id = user.getId();

        userDAO.delete(user);
        assertNull(userDAO.getById(id));
    }
}