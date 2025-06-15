package org.example;

import org.example.dao.UserDAOImpl;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserDAOImpl userDAO = new UserDAOImpl();

    public static void main(String[] args) {
        log.info("Приложение запущено");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readIntInput("Выберите действие: ");

            switch (choice) {
                case 1 -> createUser();
                case 2 -> getUserById();
                case 3 -> getAllUsers();
                case 4 -> updateUser();
                case 5 -> deleteUser();
                case 0 -> {
                    log.info("Завершение работы...");
                    running = false;
                }
                default -> log.warn("Неверный выбор, попробуйте снова");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n    Консоль управления");
        System.out.println("1. Создать пользователя");
        System.out.println("2. Найти пользователя по ID");
        System.out.println("3. Показать всех пользователей");
        System.out.println("4. Изменить пользователя");
        System.out.println("5. Удалить пользователя");
        System.out.println("0. Выход");
    }

    private static void createUser() {
        log.info("Создание нового пользователя...");
        String name = readStringInput("Введите имя: ");
        String email = readStringInput("Введите email: ");
        int age = readIntInput("Введите возраст: ");

        User user = new User(name, email, age, OffsetDateTime.now());
        userDAO.create(user);
        log.info("Создан пользователь: {}", user);
    }

    private static void getUserById() {
        int id = readIntInput("Введите ID пользователя: ");
        User user = userDAO.getById(id);
        if (user != null) {
            log.info("Найден пользователь: {}", user);
        } else {
            log.warn("Пользователь с ID {} не найден", id);
        }
    }

    private static void getAllUsers() {
        List<User> users = userDAO.getAll();
        if (users.isEmpty()) {
            log.info("Нет пользователей в базе");
        } else {
            log.info("Список пользователей ({}):", users.size());
            users.forEach(user -> log.info(" - {}", user));
        }
    }

    private static void updateUser() {
        int id = readIntInput("Введите ID пользователя : ");
        User user = userDAO.getById(id);
        if (user == null) {
            log.warn("Пользователь с ID {} не найден", id);
            return;
        }

        log.info("Текущие данные: {}", user);
        user.setName(readStringInput("Новое имя (оставьте пустым, чтобы не менять): ", user.getName()));
        user.setEmail(readStringInput("Новый email (оставьте пустым, чтобы не менять): ", user.getEmail()));
        user.setAge(readIntInput("Новый возраст : ", user.getAge()));



        userDAO.update(user);
        log.info("Пользователь обновлен: {}", user);
    }

    private static void deleteUser() {
        int id = readIntInput("Введите ID пользователя для удаления: ");
        User user = userDAO.getById(id);
        if (user != null) {
            userDAO.delete(user);
            log.info("Пользователь {} удален", user);
        } else {
            log.warn("Пользователь с ID {} не найден", id);
        }
    }

    // Вспомогательные методы для ввода
    private static String readStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static String readStringInput(String prompt, String defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        return input.isEmpty() ? defaultValue : input;
    }

    private static int readIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                log.warn("Ошибка: введите целое число");
            }
        }
    }

    private static int readIntInput(String prompt, int defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            log.warn("Ошибка: введите целое число");
            return defaultValue;
        }
    }
}