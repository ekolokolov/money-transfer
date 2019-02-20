package ea.kolokolov.adapter;

import ea.kolokolov.model.Account;
import org.jooq.Record;
import org.jooq.RecordMapper;

import static ea.kolokolov.jooq.tables.Account.ACCOUNT;

/**
 * Transform {@link Record} to @{@link Account}
 */
public class AccountAdapter implements RecordMapper<Record, Account> {

    @Override
    public Account map(Record record) {
        Account account = new Account();
        account.setId(record.get(ACCOUNT.ID));
        account.setUserId(record.get(ACCOUNT.USER_ID));
        account.setAccountId(record.get(ACCOUNT.ACCOUNT_NUMBER));
        account.setBalance(record.get(ACCOUNT.BALANCE));
        return account;
    }
}
