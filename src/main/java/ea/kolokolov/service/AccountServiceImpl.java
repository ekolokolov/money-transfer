package ea.kolokolov.service;

import ea.kolokolov.dao.AccountDao;
import ea.kolokolov.model.Account;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao;

    @Inject
    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Account getAccount(Integer userId) {
        Account account = accountDao.getAccount(userId);
        if (account == null) {
            throw new RuntimeException();
        }
        return account;
    }

    @Override
    public List<Account> getAccounts(String login) {
        List<Account> accounts = accountDao.getAccounts(login);
        if (accounts.isEmpty()) {
            throw new RuntimeException();
        }
        return accounts;
    }

    @Override
    public Account createAccount(Account account) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Account deleteAccount(Integer accountId) {
        throw new UnsupportedOperationException();
    }
}
