package ea.kolokolov.service;


import ea.kolokolov.dao.UserDao;
import ea.kolokolov.model.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class UserInfoServiceImplTest {

    private static final String USER_LOGIN = "user_login";
    private static final int USER_ID = 15;
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_SECOND_NAME = "User_second_name";

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserInfoServiceImpl service;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUsers() {
        //given
        List<User> expectedUsers = new ArrayList<>();
        User user = new User();
        user.setId(USER_ID);
        user.setLogin(USER_LOGIN);
        user.setName(USER_NAME);
        user.setSecondName(USER_SECOND_NAME);
        expectedUsers.add(new User());
        expectedUsers.add(user);
        //when
        when(userDao.getAllUsers()).thenReturn(expectedUsers);
        //then
        List<User> result = service.getUsers();
        assertNotNull(result);
        assertEquals(result, expectedUsers);
        for (int i = 0; i < result.size(); i++) {
            assertEquals(result.get(i), expectedUsers.get(i));
        }
    }

    @Test
    public void getUser() {
        //given
        User newUser = new User();
        newUser.setId(USER_ID);
        newUser.setLogin(USER_LOGIN);
        newUser.setName(USER_NAME);
        newUser.setSecondName(USER_SECOND_NAME);
        //when
        when(userDao.getUser(USER_LOGIN)).thenReturn(newUser);
        //then
        User user = service.getUser(USER_LOGIN);
        assertEquals(user, newUser);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void createUser() {
        User user = new User();
        user.setLogin("angryPony");
        user.setName("Peter");
        user.setSecondName("Dagger");
        //then
        service.createUser(user);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void deleteUser() {
        //then
        service.deleteUser(USER_LOGIN);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void changeUser() {
        //given
        User user = new User();
        user.setLogin("google");
        user.setName("Igor");
        user.setSecondName("Nikolaev");
        //then
        service.changeUser(user);
    }

}
