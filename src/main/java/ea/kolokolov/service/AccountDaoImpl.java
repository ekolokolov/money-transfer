package ea.kolokolov.service;

import ea.kolokolov.adapter.AccountAdapter;
import ea.kolokolov.model.Account;
import ea.kolokolov.model.User;
import org.jooq.DSLContext;
import org.modelmapper.ModelMapper;
import org.modelmapper.jooq.RecordValueReader;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static ea.kolokolov.jooq.tables.Account.ACCOUNT;
import static ea.kolokolov.jooq.tables.UserInfo.USER_INFO;


public class AccountDaoImpl implements AccountDao {

    private DSLContext context;
    private AccountAdapter adapter = new AccountAdapter();

    @Inject
    public AccountDaoImpl(DSLContext context) {
        this.context = context;
    }

    @Override
    public User getUserInfo(Integer id) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().addValueReader(new RecordValueReader());

        Map<User, List<Account>> userListMap = context.select()
                .from(USER_INFO)
                .leftJoin(ACCOUNT).on(ACCOUNT.USER_ID.eq(USER_INFO.ID))
                .where(USER_INFO.ID.eq(id))
                .fetch().intoGroups(User.class, Account.class);
        return adapter.map(userListMap);
    }

    @Override
    public String transfer(Integer from, Integer to, BigDecimal count) {
        context.update(ACCOUNT).set(ACCOUNT.BALANCE, count).where(ACCOUNT.USER_ID.eq(to)).execute();
        return "OK";
    }

}
