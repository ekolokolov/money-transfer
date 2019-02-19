package ea.kolokolov.dao;

import ea.kolokolov.adapter.TransactionAdapter;
import ea.kolokolov.model.Transaction;
import ea.kolokolov.model.TransactionStatus;
import org.jooq.DSLContext;

import javax.inject.Inject;
import java.util.List;

import static ea.kolokolov.jooq.tables.Transactions.TRANSACTIONS;

public class TransactionDaoImpl implements TransactionDao {

    private DSLContext context;

    @Inject
    public TransactionDaoImpl(DSLContext context) {
        this.context = context;
    }

    @Override
    public List<Transaction> getAllTransactions(Integer accountId) {
        return context.select()
                .from(TRANSACTIONS)
                .where(TRANSACTIONS.ACCOUNT_FROM_ID.eq(accountId))
                .fetch(new TransactionAdapter());
    }

    @Override
    public Transaction getTransaction(Integer transactionId) {
        return null;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        context.insertInto(TRANSACTIONS,
                TRANSACTIONS.TRANSACTION_ID,
                TRANSACTIONS.ACCOUNT_FROM_ID,
                TRANSACTIONS.ACCOUNT_TO_ID,
                TRANSACTIONS.AMOUNT)
                .values(
                        56789,
                        transaction.getFrom(),
                        transaction.getTo(),
                        transaction.getCount())
                .execute();
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setTransactionNumber("235213600000");
        return transaction;
    }
}
