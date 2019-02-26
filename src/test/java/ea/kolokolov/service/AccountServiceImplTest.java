package ea.kolokolov.service;

import ea.kolokolov.dao.AccountDao;
import ea.kolokolov.model.Account;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountServiceImplTest {

    private static final BigDecimal BALANCE = new BigDecimal(6642436).setScale(2, HALF_UP);
    private static final int ACCOUNT_NUMBER = 249869986;
    private static final int USER_ID = 2;

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
        Assert.assertEquals(actualAccount, account);

    }

    @Test
    public void testGetAccounts() {
    }

    @Test
    public void testCreateAccount() {
    }

    @Test
    public void testDeleteAccount() {
    }
}