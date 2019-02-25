package ea.kolokolov.dao;


import ea.kolokolov.config.DBConfig;
import ea.kolokolov.model.User;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.List;

import static org.testng.Assert.*;

public class UserDaoImplTest {
    private static final String UNCORRECTED_USER_LOGIN = "user_login_do_not_exist";

    private static final String USER_LOGIN = "iivanov";
    private static final String USER_SECOND_NAME = "Ivanov";
    private static final String USER_NAME = "Ivan";

    private UserDaoImpl userDao;

    @BeforeTest
    public void setUp() throws SQLException {
        userDao = new UserDaoImpl(DBConfig.context());
    }

    @Test
    public void getExistedUser() {
        User user = userDao.getUser(USER_LOGIN);
        assertNotNull(user);
        assertEquals(user.getName(), USER_NAME);
        assertEquals(user.getSecondName(), USER_SECOND_NAME);
        assertEquals(user.getLogin(), USER_LOGIN);
    }

    @Test
    public void getUnExistedUser() {
        User user = userDao.getUser(UNCORRECTED_USER_LOGIN);
        assertNull(user);
    }

    @Test
    public void getAllUsers() {
        List<User> allUsers = userDao.getAllUsers();
        assertNotNull(allUsers);
        assertEquals(allUsers.size(), 4);
    }

}
