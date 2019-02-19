package ea.kolokolov.service;

import ea.kolokolov.model.Account;

import java.util.List;

public interface AccountService {

    Account getAccount(Integer userId);

    List<Account> getAccounts(String login);

}
