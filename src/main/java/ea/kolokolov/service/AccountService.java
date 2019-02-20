package ea.kolokolov.service;

import ea.kolokolov.model.Account;

import java.util.List;

/**
 * Service for operations with Account
 */
public interface AccountService {

    /**
     * Get account with id
     *
     * @param accountId unique identifier
     * @return Account or null
     */
    Account getAccount(Integer accountId);

    /**
     * Get all accounts for User
     *
     * @param login user login
     * @return List Accounts or empty List
     */
    List<Account> getAccounts(String login);

    /**
     * Create new Account
     *
     * @param account data for new Account
     * @return new created account
     */
    Account createAccount(Account account);

    /**
     * Delete account with number
     *
     * @param accountId account number
     * @return deleted account
     */
    Account deleteAccount(Integer accountId);

}
