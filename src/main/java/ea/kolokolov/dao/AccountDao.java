package ea.kolokolov.dao;

import ea.kolokolov.model.Account;
import org.jooq.Configuration;

import java.util.List;

/**
 * DA layer for access to Account
 */
public interface AccountDao {

    /**
     * Get one account with accountNumber
     *
     * @param accountNumber unique number for account
     * @return Account or null
     */
    Account getAccount(Integer accountNumber);

    /**
     * Get one account with accountNumber
     *
     * @param accountNumber unique number for account
     * @param configuration {@link Configuration} for executing in DB-transaction
     * @return Account or null
     */
    Account getAccount(Integer accountNumber, Configuration configuration);

    /**
     * Get all accounts for user with login
     *
     * @param login unique String key for user
     * @return List of Account or empty List
     */
    List<Account> getAccounts(String login);

    /**
     * Update account
     *
     * @param account       new data to Account
     * @param configuration {@link Configuration} for executing in DB-transaction
     * @return new updated Account
     */
    Account updateAccount(Account account, Configuration configuration);

}
