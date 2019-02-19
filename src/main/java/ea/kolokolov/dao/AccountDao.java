package ea.kolokolov.dao;

import ea.kolokolov.model.Account;

import java.util.List;

public interface AccountDao {

    Account getAccount(Integer userId);

    List<Account> getAccounts(String login);
}
