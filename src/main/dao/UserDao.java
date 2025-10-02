package dao;

import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HibernateSessionFactoryUtil;
import java.util.List;

public class UserDao {

    private final SessionFactory sessionFactory;

    public UserDao() {
        this.sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    // Конструктор для тестов
    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    public User findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    public void save(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(user);
                tx.commit();
                logger.info("User saved successfully: {}", user.getId());
            } catch (Exception e) {
                tx.rollback();
                logger.error("Error saving user", e);
                throw new RuntimeException("Error saving user", e);
            }
        }
    }

    public void update(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.merge(user);
                tx.commit();
                logger.info("User updated successfully: {}", user.getId());
            } catch (Exception e) {
                tx.rollback();
                logger.error("Error updating user", e);
                throw new RuntimeException("Error updating user", e);
            }
        }
    }

    public void delete(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.remove(user);
                tx.commit();
                logger.info("User deleted successfully: {}", user.getId());
            } catch (Exception e) {
                tx.rollback();
                logger.error("Error deleting user", e);
                throw new RuntimeException("Error deleting user", e);
            }
        }
    }

    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).getResultList();
        }
    }
}
