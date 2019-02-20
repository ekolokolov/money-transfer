package ea.kolokolov.service;

import ea.kolokolov.dao.UserDao;
import ea.kolokolov.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class UserInfoServiceImpl implements UserInfoService {

    private UserDao userDao;

    @Inject
    public UserInfoServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public User getUser(String login) {
        return userDao.getUser(login);
    }

    @Override
    public User createUser(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User deleteUser(String login) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User changeUser(User user) {
        throw new UnsupportedOperationException();
    }

}
