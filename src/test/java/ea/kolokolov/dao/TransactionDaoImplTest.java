package ea.kolokolov.dao;

import ea.kolokolov.config.DBConfig;
import ea.kolokolov.model.Transaction;
import ea.kolokolov.model.TransactionStatus;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class TransactionDaoImplTest {

    private static final Integer FROM = 778999007;
    private static final Integer TO = 654124124;
    private static final BigDecimal AMOUNT = new BigDecimal(100).setScale(2, RoundingMode.HALF_UP);
    private TransactionDaoImpl transactionDao;
    private Configuration configuration;
    private UUID uuid;

    @BeforeClass
    public void setUp() throws SQLException {
        DSLContext context = DBConfig.context();
        configuration = context.configuration();
        transactionDao = new TransactionDaoImpl(context);
    }


    @Test
    public void testCreateTransaction() {
        //given
        Transaction transaction = new Transaction();
        transaction.setFrom(FROM);
        transaction.setTo(TO);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setCount(AMOUNT);

        //then
        Transaction savedTransaction = transactionDao.createTransaction(transaction, configuration);
        assertNotNull(savedTransaction);
        assertEquals(savedTransaction.getFrom(), FROM);
        assertEquals(savedTransaction.getTo(), TO);
        assertEquals(savedTransaction.getCount(), AMOUNT);
        assertEquals(savedTransaction.getStatus(), TransactionStatus.SUCCESS);
        assertNotNull(savedTransaction.getTransactionNumber());
        uuid = savedTransaction.getTransactionNumber();
    }

    @Test(dependsOnMethods = "testCreateTransaction",
            dataProvider = "transactions")
    public void testGetAllTransactions(Integer accountId, boolean exist) {
        List<Transaction> allTransactions = transactionDao.getAllTransactions(accountId);
        assertEquals(!allTransactions.isEmpty(), exist);
    }

    @Test(dependsOnMethods = "testCreateTransaction")
    public void testGetTransaction() {
        Transaction transaction = transactionDao.getTransaction(uuid);
        assertNotNull(transaction);
    }

    @DataProvider
    private Object[][] transactions() {
        return new Object[][]{
                {654124124, false},
                {778999007, true},
                {869866778, false},
                {990066778, false}
        };
    }

}