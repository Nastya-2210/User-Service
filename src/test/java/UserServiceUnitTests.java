import dao.UserDao;
import jakarta.transaction.Transactional;
import models.User;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import services.UserService;


class UserServiceUnitTests {
    private UserDao mockUserDao;
    private UserService userService;

    @BeforeEach
    void setUp(){
        mockUserDao = Mockito.mock(UserDao.class);
        userService = new UserService();
        userService.setUserDao(mockUserDao);
    }

    @Test
    void shouldSaveUserToDatabase(){
        User userToSave = new User("Ariel", 33, "email@gmail.com");

        Mockito.doNothing().when(mockUserDao).save(userToSave);

        userService.saveUser(userToSave);

        Mockito.verify(mockUserDao).save(userToSave);
    }

    @Test
    void shouldDeleteUserFromDatabase(){
    User userToDelete = new User("Tomas", 46, "Ttt@com");

    Mockito.doNothing().when(mockUserDao).delete(userToDelete);

    userService.deleteUser(userToDelete);

    Mockito.verify(mockUserDao).delete(userToDelete);
    }

    @Test
    void shouldUpdateUserInDatabase() {
        // given
        User userToUpdate = new User("Ben", 76, "ben@gmail.com");
        userToUpdate.setId(5);

        Mockito.when(mockUserDao.findById(5)).thenReturn(userToUpdate);
        Mockito.doNothing().when(mockUserDao).update(userToUpdate);

        userService.updateUser(userToUpdate);

            Mockito.verify(mockUserDao).update(userToUpdate);
    }
}
