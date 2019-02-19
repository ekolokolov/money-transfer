package ea.kolokolov.dao;

import ea.kolokolov.adapter.AccountAdapter;
import ea.kolokolov.model.Account;
import org.jooq.DSLContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import static ea.kolokolov.jooq.Tables.ACCOUNT;
import static ea.kolokolov.jooq.Tables.USER_INFO;


@Singleton
public class AccountDaoImpl implements AccountDao {


    private DSLContext context;

    @Inject
    public AccountDaoImpl(DSLContext context) {
        this.context = context;
    }


    @Override
    public Account getAccount(Integer accountId) {
        return context.select().from(ACCOUNT)
                .where(ACCOUNT.ACCOUNT_NUMBER.eq(accountId))
                .fetchOne(new AccountAdapter());
    }

    @Override
    public List<Account> getAccounts(String login) {
        return context.select().from(ACCOUNT)
                .join(USER_INFO).on(ACCOUNT.USER_ID.eq(USER_INFO.ID))
                .where(USER_INFO.LOGIN.eq(login))
                .fetch(new AccountAdapter());
    }
}
