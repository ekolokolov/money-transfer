package ea.kolokolov.service;

import ea.kolokolov.dao.AccountDao;
import ea.kolokolov.model.Account;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.RoundingMode.HALF_UP;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class AccountServiceImplTest {

    private static final BigDecimal BALANCE = new BigDecimal(6642436).setScale(2, HALF_UP);
    private static final int ACCOUNT_NUMBER = 249869986;
    private static final int USER_ID = 2;
    private static final String LOGIN = "iivanov";

    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private AccountServiceImpl service;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAccount() {
        //given
        Account account = new Account(USER_ID, ACCOUNT_NUMBER, BALANCE);
        //when
        when(accountDao.getAccount(ACCOUNT_NUMBER)).thenReturn(account);
        //then
        Account actualAccount = service.getAccount(ACCOUNT_NUMBER);
        verify(accountDao).getAccount(ACCOUNT_NUMBER);
        assertEquals(actualAccount, account);

    }

    @Test
    public void testGetAccounts() {
        //given
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(USER_ID, ACCOUNT_NUMBER, BALANCE));
        //when
        when(accountDao.getAccounts(LOGIN)).thenReturn(accounts);
        //then
        List<Account> actualAccounts = service.getAccounts(LOGIN);
        verify(accountDao).getAccounts(LOGIN);
        assertFalse(actualAccounts.isEmpty());
        assertEquals(actualAccounts, accounts);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testCreateAccount() {
        //then
        service.createAccount(new Account());
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testDeleteAccount() {
        //then
        service.deleteAccount(ACCOUNT_NUMBER);
    }
}