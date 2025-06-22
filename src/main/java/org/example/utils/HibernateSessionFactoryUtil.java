package org.example.utils;

import org.example.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.Properties;

public class HibernateSessionFactoryUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static String getEnvOrProperty(String key) {
        String value = System.getProperty(key);
        if (value == null) {
            value = System.getenv(key);
        }
        return value;
    }

    private static SessionFactory buildSessionFactory() {
        try {
            Properties props = new Properties();
            props.load(HibernateSessionFactoryUtil.class.getClassLoader().getResourceAsStream("hibernate.properties"));

            String dbUrl = getEnvOrProperty("DB_URL");
            String dbUser = getEnvOrProperty("DB_USERNAME");
            String dbPassword = getEnvOrProperty("DB_PASSWORD");

            if (dbUrl == null || dbUser == null || dbPassword == null) {
                throw new RuntimeException("Переменные окружения DB_URL, DB_USERNAME или DB_PASSWORD не установлены.");
            }

            props.setProperty("hibernate.connection.url", dbUrl);
            props.setProperty("hibernate.connection.username", dbUser);
            props.setProperty("hibernate.connection.password", dbPassword);

            return new Configuration()
                    .addProperties(props)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Ошибка при создании SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}