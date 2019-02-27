package ea.kolokolov.dao;

import ea.kolokolov.config.DBConfig;
import ea.kolokolov.model.Account;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.testng.Assert.*;

public class AccountDaoImplTest {

    private static final Integer ACCOUNT_NUMBER = 990066778;
    private static final BigDecimal ACCOUNT_BALANCE = new BigDecimal(2000.10).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final int ACCOUNT_NUMBER_NOT_EXIST = 9999999;
    private AccountDaoImpl accountDao;
    private Configuration configuration;

    @BeforeClass
    public void setUp() throws SQLException {
        DSLContext context = DBConfig.context();
        configuration = context.configuration();
        accountDao = new AccountDaoImpl(context);
    }

    @Test
    public void testGetAccount() {
        Account account = accountDao.getAccount(ACCOUNT_NUMBER);
        assertNotNull(account);
        assertEquals(account.getAccountId(), ACCOUNT_NUMBER);
        assertEquals(account.getBalance(), ACCOUNT_BALANCE);
        assertEquals(account.getUserId(), new Integer(2));
    }

    @Test
    public void testGetAccount_notExist() {
        Account account = accountDao.getAccount(ACCOUNT_NUMBER_NOT_EXIST);
        assertNull(account);
    }

    @Test
    public void testGetAccountWithTransactionConfig() {
        Account account = accountDao.getAccount(ACCOUNT_NUMBER, configuration);
        assertNotNull(account);
        assertEquals(account.getAccountId(), ACCOUNT_NUMBER);
        assertEquals(account.getBalance(), ACCOUNT_BALANCE);
        assertEquals(account.getUserId(), new Integer(2));
    }

    @Test
    public void testGetAccountWithTransactionConfig_notExist() {
        Account account = accountDao.getAccount(ACCOUNT_NUMBER_NOT_EXIST, configuration);
        assertNull(account);
    }

    @Test(dataProvider = "userAccounts")
    public void testGetAccounts(String login, int accountsCount) {
        List<Account> accounts = accountDao.getAccounts(login);
        assertEquals(accounts.size(), accountsCount);
    }


    @Test
    public void testUpdateAccount() {
        //given
        BigDecimal newBalance = new BigDecimal(1000).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal expectedBalance = new BigDecimal(2000.10).setScale(2, BigDecimal.ROUND_HALF_UP);
        Integer accountId = 120000003;

        Account account = accountDao.getAccount(accountId);

        assertEquals(account.getBalance(), expectedBalance);
        assertEquals(account.getAccountId(), accountId);

        //then
        account.setBalance(newBalance);
        Account updateAccount = accountDao.updateAccount(account, configuration);
        assertNotNull(updateAccount);
        assertEquals(updateAccount.getBalance(), newBalance);
        assertEquals(updateAccount.getAccountId(), account.getAccountId());
        assertEquals(updateAccount.getId(), account.getId());
        assertEquals(updateAccount.getUserId(), account.getUserId());
    }

    @DataProvider
    private Object[][] userAccounts() {
        return new Object[][]{
                {"iivanov", 1},
                {"petro88", 3},
                {"google", 1},
                {"vano23", 0},
                {"Iivanov", 0},
                {"petro", 0},
                {" ", 0},
                {null, 0},
        };
    }

}