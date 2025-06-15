package org.example.dao;

import org.example.dao.UserDao;
import org.example.model.User;
import org.example.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserDAOImpl implements UserDao {

    private static final Logger log = LoggerFactory.getLogger(UserDAOImpl.class);

    @Override
    public void create(User user) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            log.debug("User created: {}", user);
        } catch (Exception e) {
            log.error("Exception while creating user", e);
            throw e;
        }
    }

    @Override
    public User getById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            System.out.println("Exception while getting user by id: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            System.out.println("Exception while getting all users: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void update(User user) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            log.debug("User updated: {}", user);
        } catch (Exception e) {
            log.error("Exception while updating user", e);
            throw e;
        }
    }

    @Override
    public void delete(User user) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
            log.debug("User deleted: {}", user);
        } catch (Exception e) {
            log.error("Exception while deleting user", e);
            throw e;
        }
    }

    @Override
    public void deleteById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            transaction.commit();
            log.debug("User deleted: {}", id);
        } catch (Exception e) {
            log.error("Exception while deleting user", e);
            throw e;
        }
    }
}