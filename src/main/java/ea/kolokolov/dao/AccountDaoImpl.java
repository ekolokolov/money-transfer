package ea.kolokolov.dao;

import ea.kolokolov.adapter.AccountAdapter;
import ea.kolokolov.model.Account;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import static ea.kolokolov.jooq.Tables.ACCOUNT;
import static ea.kolokolov.jooq.Tables.USER_INFO;


@Singleton
public class AccountDaoImpl implements AccountDao {


    private static final AccountAdapter ACCOUNT_ADAPTER = new AccountAdapter();
    private DSLContext context;

    @Inject
    public AccountDaoImpl(DSLContext context) {
        this.context = context;
    }


    @Override
    public Account getAccount(Integer accountNumber) {
        return selectAccount(accountNumber, context);
    }

    @Override
    public Account getAccount(Integer accountNumber, Configuration configuration) {
        return selectAccount(accountNumber, DSL.using(configuration));
    }

    @Override
    public List<Account> getAccounts(String login) {
        return context.select().from(ACCOUNT)
                .join(USER_INFO).on(ACCOUNT.USER_ID.eq(USER_INFO.ID))
                .where(USER_INFO.LOGIN.eq(login))
                .fetch(ACCOUNT_ADAPTER);
    }

    @Override
    public Account updateAccount(Account account, Configuration configuration) {
        return update(account, DSL.using(configuration));
    }

    /**
     * Execute Account selected statement
     *
     * @param accountNumber unique account id
     * @param context       DB executing context
     * @return Account or null
     */
    private Account selectAccount(Integer accountNumber, DSLContext context) {
        return context.select().from(ACCOUNT)
                .where(ACCOUNT.ACCOUNT_NUMBER.eq(accountNumber))
                .fetchOne(new AccountAdapter());
    }

    /**
     * Update account information
     *
     * @param account Account information
     * @param context DB executing context
     * @return updated Account
     */
    private Account update(Account account, DSLContext context) {
        context.update(ACCOUNT)
                .set(ACCOUNT.BALANCE, account.getBalance())
                .where(ACCOUNT.ACCOUNT_NUMBER.eq(account.getAccountId()))
                .execute();
        return account;
    }
}