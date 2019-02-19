package ea.kolokolov.dao;

import ea.kolokolov.model.User;
import org.jooq.DSLContext;

import javax.inject.Inject;
import java.util.List;

import static ea.kolokolov.jooq.tables.UserInfo.USER_INFO;


public class UserDaoImpl implements UserDao {

    private DSLContext context;

    @Inject
    public UserDaoImpl(DSLContext context) {
        this.context = context;
    }

    @Override
    public User getUser(String login) {
        return context.selectFrom(USER_INFO)
                .where(USER_INFO.LOGIN.eq(login))
                .fetchOneInto(User.class);
    }

    @Override
    public List<User> getUsers() {
        return context.selectFrom(USER_INFO)
                .fetchInto(User.class);
    }

}
