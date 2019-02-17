package ea.kolokolov.adapter;

import ea.kolokolov.model.Account;
import ea.kolokolov.model.User;

import java.util.List;
import java.util.Map;


public class AccountAdapter {

    public User map(Map<User, List<Account>> record) {
        for (User user : record.keySet()) {
            List<Account> accounts = record.get(user);
            user.setAccounts(accounts);
            return user;
        }
        return null;
    }
}
