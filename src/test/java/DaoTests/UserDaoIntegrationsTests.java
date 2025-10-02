package DaoTests;

import dao.UserDao;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class UserDaoIntegrationsTests extends BaseDaoIntegrationsTests {
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        userDao = new UserDao(sessionFactory);
    }

    @Test
    void shouldSaveUserToRealDatabase() {
        // given
        User user = new User("Test User", 25, "test@email.com");

        // when
        userDao.save(user);

        // then
        User savedUser = userDao.findById(user.getId());
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals("Test User", savedUser.getName());
    }

    @Test
    void shouldDeleteUserFromRealDatabase(){
        // given
        User user = new User("Test User", 25, "test@email.com");
        userDao.save(user);
        int userId = user.getId();

        // when
        userDao.delete(user);

        // then
        User deletedUser = userDao.findById(userId);
        Assertions.assertNull(deletedUser);
    }

    @Test
    void shouldUpdateUserInRealDatabase(){
        // given
        User user = new User("Test User", 25, "test@email.com");
        userDao.save(user);
        int userId = user.getId();

        // when
        user.setName("Ivan");
        user.setEmail("ivan@email.com"); // ← Меняем несколько полей
        userDao.update(user);

        // then
        User updatedUserFromDb = userDao.findById(userId);
        Assertions.assertNotNull(updatedUserFromDb);
        Assertions.assertEquals("Ivan", updatedUserFromDb.getName());
        Assertions.assertEquals("ivan@email.com", updatedUserFromDb.getEmail());
    }

}
