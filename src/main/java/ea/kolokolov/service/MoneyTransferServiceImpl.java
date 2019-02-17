package ea.kolokolov.service;

import javax.inject.Inject;
import java.math.BigDecimal;

public class MoneyTransferServiceImpl implements MoneyTransferService {

    private AccountDao accountDao;

    @Inject
    public MoneyTransferServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public String transfer(Integer from, Integer to, BigDecimal count) {
        return "OK";
    }
}
