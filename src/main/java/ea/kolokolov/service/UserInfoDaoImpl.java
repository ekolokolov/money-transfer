package ea.kolokolov.service;

import ea.kolokolov.adapter.AccountAdapter;
import ea.kolokolov.exception.OperationForbiddenException;
import ea.kolokolov.model.Account;
import ea.kolokolov.model.User;
import org.jooq.DSLContext;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static ea.kolokolov.jooq.tables.Account.ACCOUNT;
import static ea.kolokolov.jooq.tables.UserInfo.USER_INFO;


public class UserInfoDaoImpl implements AccountDao {

    private DSLContext context;
    private AccountAdapter accountAdapter = new AccountAdapter();

    @Inject
    public UserInfoDaoImpl(DSLContext context) {
        this.context = context;
    }


    @Override
    public User getUserInfo(String login) {
        User user = getUserByLogin(login);
        if (user == null) return null;
        user.setAccounts(getAccountsByUser(user));
        return user;
    }

    private User getUserByLogin(String id) {
        return context.selectFrom(USER_INFO)
                .where(USER_INFO.LOGIN.eq(id))
                .fetchOneInto(User.class);
    }

    private List<Account> getAccountsByUser(User user) {
        return context.selectFrom(ACCOUNT)
                .where(ACCOUNT.USER_ID.eq(user.getId()))
                .fetch(accountAdapter);
    }


    @Override
    public String transfer(Integer from, Integer to, BigDecimal count) {
        context.update(ACCOUNT).set(ACCOUNT.BALANCE, count).where(ACCOUNT.USER_ID.eq(to)).execute();
        if (from.equals(12)) {
            throw new OperationForbiddenException("Account have not enough money.");
        }
        return "OK";
    }

}
