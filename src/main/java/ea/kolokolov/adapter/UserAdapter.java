package ea.kolokolov.adapter;

import ea.kolokolov.model.User;
import org.jooq.Record;
import org.jooq.RecordMapper;

import static ea.kolokolov.jooq.tables.UserInfo.USER_INFO;


/**
 * Transform {@link Record} to {@link User}
 */
public class UserAdapter implements RecordMapper<Record, User> {


    @Override
    public User map(Record record) {
        User user = new User();
        user.setId(record.get(USER_INFO.ID));
        user.setName(record.get(USER_INFO.NAME));
        user.setSecondName(record.get(USER_INFO.SECOND_NAME));
        return user;
    }
}
