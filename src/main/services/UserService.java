package services;

import dao.UserDao;
import models.User;

import java.util.List;

public class UserService {
    private UserDao usersDao = new UserDao();

    // Setter для тестов
    public void setUserDao(UserDao userDao) {
        this.usersDao = userDao;
    }

    public User findUser(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        return usersDao.findById(id);
    }

    public void saveUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        usersDao.save(user);
    }

    public void deleteUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        usersDao.delete(user);
    }

    public void updateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        // Проверяем, существует ли пользователь
        User existingUser = usersDao.findById(user.getId());
        if (existingUser == null) {
            throw new IllegalArgumentException("User with ID " + user.getId() + " does not exist");
        }
        usersDao.update(user);
    }

    public List<User> findAllUsers() {
        return usersDao.findAll();
    }
}

