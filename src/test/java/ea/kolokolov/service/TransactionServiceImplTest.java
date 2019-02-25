package ea.kolokolov.service;

import ea.kolokolov.config.CustomMockDataProvider;
import ea.kolokolov.dao.AccountDao;
import ea.kolokolov.dao.TransactionDao;
import ea.kolokolov.model.Account;
import ea.kolokolov.model.Transaction;
import ea.kolokolov.model.TransactionStatus;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class TransactionServiceImplTest {

    private final int FROM = 654124124;
    private final int TO = 778999007;
    private final BigDecimal COUNT = new BigDecimal(50.50);
    private final UUID TRANSACTION_NUMBER = UUID.randomUUID();

    @Mock
    private AccountDao accountDao;

    @Mock
    private TransactionDao transactionDao;

    private DSLContext context;

    private TransactionServiceImpl service;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockDataProvider dataProvider = new CustomMockDataProvider();
        MockConnection mockConnection = new MockConnection(dataProvider);
        context = DSL.using(mockConnection, SQLDialect.H2);
        service = new TransactionServiceImpl(transactionDao, accountDao, context);
    }

    @Test
    public void testGetAllTransactions() {
        //then
        service.getAllTransactions(FROM);
    }

    @Test
    public void testGetTransaction() {
        //then
    }

    @Test
    public void testExecute_SUCCESS_Transaction() {
        //given
        Transaction transaction = createNewTransaction();
        Account srcAccount = new Account();
        srcAccount.setId(1);
        srcAccount.setBalance(new BigDecimal(1000.12));
        srcAccount.setAccountId(FROM);
        srcAccount.setUserId(33);
        Account distAccount = new Account();
        distAccount.setId(2);
        distAccount.setBalance(new BigDecimal(2000.00));
        distAccount.setAccountId(TO);
        distAccount.setUserId(33);


        //when
        Configuration configuration = context.configuration();
        when(accountDao.getAccount(FROM, configuration)).thenReturn(srcAccount);
        when(accountDao.getAccount(TO, configuration)).thenReturn(distAccount);
        when(accountDao.updateAccount(srcAccount, configuration)).thenReturn(distAccount);
        when(accountDao.updateAccount(distAccount, configuration)).thenReturn(distAccount);
        when(transactionDao.createTransaction(transaction, configuration))
                .thenReturn(addTransactionNumber(transaction));

        //then
        Transaction actual = service.executeTransaction(transaction);
        assertEquals(actual.getCount(), transaction.getCount());
        assertEquals(actual.getFrom(), transaction.getFrom());
        assertEquals(actual.getTo(), transaction.getTo());
        assertEquals(actual.getStatus(), TransactionStatus.SUCCESS);
    }

    @Test
    public void testExecute_FAIL_Transaction() {
        //given
        Transaction transaction = createNewTransaction();
        Account srcAccount = new Account();
        srcAccount.setId(1);
        srcAccount.setBalance(new BigDecimal(1000.12));
        srcAccount.setAccountId(FROM);
        srcAccount.setUserId(33);
        Account distAccount = new Account();
        distAccount.setId(2);
        distAccount.setBalance(new BigDecimal(2000.00));
        distAccount.setAccountId(TO);
        distAccount.setUserId(33);


        //when
        Configuration configuration = context.configuration();
        when(accountDao.getAccount(FROM, configuration)).thenReturn(null);
        when(accountDao.getAccount(TO, configuration)).thenReturn(null);
        when(accountDao.updateAccount(srcAccount, configuration)).thenReturn(distAccount);
        when(accountDao.updateAccount(distAccount, configuration)).thenReturn(distAccount);
        when(transactionDao.createTransaction(transaction, configuration))
                .thenReturn(addTransactionNumber(transaction));

        //then
        Transaction actual = service.executeTransaction(transaction);
        assertEquals(actual.getCount(), transaction.getCount());
        assertEquals(actual.getFrom(), transaction.getFrom());
        assertEquals(actual.getTo(), transaction.getTo());
        assertEquals(actual.getStatus(), TransactionStatus.FAIL);
        assertEquals(actual.getTransactionNumber(), TRANSACTION_NUMBER);
    }


    private Transaction addTransactionNumber(Transaction transaction) {
        transaction.setTransactionNumber(TRANSACTION_NUMBER);
        return transaction;
    }

    private Transaction createNewTransaction() {
        Transaction newTransaction = new Transaction();
        newTransaction.setFrom(FROM);
        newTransaction.setTo(TO);
        newTransaction.setCount(COUNT);
        return newTransaction;
    }
}