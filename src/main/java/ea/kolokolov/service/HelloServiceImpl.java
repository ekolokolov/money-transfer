package ea.kolokolov.service;

import ea.kolokolov.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HelloServiceImpl implements HelloService {

    private AccountDao accountDao;

    @Inject
    public HelloServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public User sayHello(Integer id) {
        return accountDao.getUserInfo(id);
    }

}
