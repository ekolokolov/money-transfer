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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.math.RoundingMode.HALF_UP;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class TransactionServiceImplTest {

    private static final BigDecimal SRC_BALANCE = new BigDecimal(3000.12).setScale(2, HALF_UP);
    private static final BigDecimal DIST_BALANCE = new BigDecimal(2000.00).setScale(2, HALF_UP);
    private static final BigDecimal UNAVAILABLE_AMOUNT = new BigDecimal(10000000000.10).setScale(2, HALF_UP);
    private static final BigDecimal NEGATIVE_AVAILABLE_AMOUNT = new BigDecimal(-1000).setScale(2, HALF_UP);
    private static final BigDecimal AVAILABLE_AMOUNT = new BigDecimal(50.50).setScale(2, HALF_UP);
    private static final int FROM = 654124124;
    private static final int TO = 778999007;
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
        //given
        List<Transaction> transactions = new ArrayList<>();
        Transaction expected = new Transaction(FROM, TO, AVAILABLE_AMOUNT);
        expected.setStatus(TransactionStatus.SUCCESS);
        expected.setTransactionNumber(TRANSACTION_NUMBER);
        transactions.add(expected);
        //when
        when(transactionDao.getAllTransactions(FROM)).thenReturn(transactions);
        //then
        List<Transaction> allTransactions = service.getAllTransactions(FROM);
        assertFalse(allTransactions.isEmpty());
        Transaction actual = allTransactions.get(0);
        assertEquals(actual, expected);
        verify(transactionDao).getAllTransactions(FROM);
    }

    @Test
    public void testGetTransaction() {
        //given
        Transaction expected = new Transaction(FROM, TO, AVAILABLE_AMOUNT);
        expected.setTransactionNumber(TRANSACTION_NUMBER);
        expected.setStatus(TransactionStatus.FAIL);

        //when
        when(transactionDao.getTransaction(TRANSACTION_NUMBER)).thenReturn(expected);

        //then
        Transaction actual = service.getTransaction(TRANSACTION_NUMBER);
        assertEquals(actual, expected);
        verify(transactionDao).getTransaction(TRANSACTION_NUMBER);

    }

    @Test(dataProvider = "transactionsData")
    public void testExecuteTransaction_withExistingAccounts(Transaction transaction, TransactionStatus status) {
        //given
        Account srcAccount = new Account(33, FROM, SRC_BALANCE);
        Account distAccount = new Account(21, TO, DIST_BALANCE);

        //when
        Configuration configuration = context.configuration();
        when(accountDao.getAccount(eq(FROM), any(Configuration.class))).thenReturn(srcAccount);
        when(accountDao.getAccount(eq(TO), any(Configuration.class))).thenReturn(distAccount);

        when(transactionDao.createTransaction(transaction, configuration))
                .thenReturn(addTransactionNumber(transaction));

        //then
        Transaction executeTransaction = service.executeTransaction(transaction);
        assertEquals(executeTransaction.getCount(), transaction.getCount());
        assertEquals(executeTransaction.getFrom(), transaction.getFrom());
        assertEquals(executeTransaction.getTo(), transaction.getTo());
        assertEquals(executeTransaction.getStatus(), status);
        assertEquals(executeTransaction.getTransactionNumber(), TRANSACTION_NUMBER);
        if (transaction.getStatus().equals(TransactionStatus.SUCCESS)) {
            verify(accountDao).updateAccount(eq(srcAccount), any(Configuration.class));
            verify(accountDao).updateAccount(eq(distAccount), any(Configuration.class));
            assertEquals(srcAccount.getBalance(), SRC_BALANCE.subtract(transaction.getCount()));
            assertEquals(distAccount.getBalance(), DIST_BALANCE.add(transaction.getCount()));
        }
    }

    @Test(dataProvider = "accountsData")
    public void testExecuteTransactionWithNotExistingAccounts(Account src, Account dst) {
        //given
        Transaction transaction = new Transaction(FROM, TO, AVAILABLE_AMOUNT);

        //when
        Configuration configuration = context.configuration();
        when(accountDao.getAccount(eq(FROM), any(Configuration.class))).thenReturn(src);
        when(accountDao.getAccount(eq(TO), any(Configuration.class))).thenReturn(dst);
        when(transactionDao.createTransaction(transaction, configuration))
                .thenReturn(addTransactionNumber(transaction));

        //then
        Transaction executeTransaction = service.executeTransaction(transaction);
        assertEquals(executeTransaction.getCount(), transaction.getCount());
        assertEquals(executeTransaction.getFrom(), transaction.getFrom());
        assertEquals(executeTransaction.getTo(), transaction.getTo());
        assertEquals(executeTransaction.getStatus(), TransactionStatus.FAIL);
        assertEquals(executeTransaction.getTransactionNumber(), TRANSACTION_NUMBER);
    }


    private Transaction addTransactionNumber(Transaction transaction) {
        transaction.setTransactionNumber(TRANSACTION_NUMBER);
        return transaction;
    }

    @DataProvider
    private Object[][] transactionsData() {
        return new Object[][]{
                {new Transaction(FROM, TO, AVAILABLE_AMOUNT), TransactionStatus.SUCCESS},
                {new Transaction(FROM, TO, NEGATIVE_AVAILABLE_AMOUNT), TransactionStatus.FAIL},
                {new Transaction(FROM, FROM, AVAILABLE_AMOUNT), TransactionStatus.FAIL},
                {new Transaction(FROM, TO, UNAVAILABLE_AMOUNT), TransactionStatus.FAIL},
        };
    }

    @DataProvider
    private Object[][] accountsData() {
        return new Object[][]{
                {new Account(33, FROM, SRC_BALANCE), null},
                {null, new Account(21, TO, DIST_BALANCE)},
                {null, null}
        };
    }

}