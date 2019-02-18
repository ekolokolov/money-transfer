package ea.kolokolov.service;

import ea.kolokolov.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserInfoServiceImpl implements UserInfoService {

    private AccountDao accountDao;

    @Inject
    public UserInfoServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public User getUserFullInfo(String login) {
        return accountDao.getUserInfo(login);
    }

}
