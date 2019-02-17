package ea.kolokolov.adapter;

import ea.kolokolov.data.Account;
import ea.kolokolov.jooq.tables.records.AccountRecord;

public class AccountAdapter {

    public Account createAccount(AccountRecord record) {
        Account account = new Account();
        account.setId(record.getId());
        account.setFamily(record.getFamily());
        account.setName(record.getName());
        return account;
    }

}
