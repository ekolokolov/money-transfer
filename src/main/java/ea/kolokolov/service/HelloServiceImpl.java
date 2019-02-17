package ea.kolokolov.service;

import ea.kolokolov.data.Account;

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
    public Account sayHello(Integer id) {
        return accountDao.getAccount(id);
    }

}
