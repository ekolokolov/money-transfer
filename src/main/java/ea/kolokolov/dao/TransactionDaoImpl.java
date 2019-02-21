package ea.kolokolov.dao;

import ea.kolokolov.adapter.TransactionAdapter;
import ea.kolokolov.model.Transaction;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;

import static ea.kolokolov.jooq.tables.Transactions.TRANSACTIONS;

@Singleton
public class TransactionDaoImpl implements TransactionDao {

    private final TransactionAdapter adapter = new TransactionAdapter();

    private final DSLContext context;

    @Inject
    public TransactionDaoImpl(DSLContext context) {
        this.context = context;
    }

    @Override
    public List<Transaction> getAllTransactions(Integer accountId) {
        return context.select()
                .from(TRANSACTIONS)
                .where(TRANSACTIONS.ACCOUNT_FROM_ID.eq(accountId))
                .fetch(adapter);
    }

    @Override
    public Transaction getTransaction(UUID transactionId) {
        return context.selectFrom(TRANSACTIONS)
                .where(TRANSACTIONS.TRANSACTION_ID.eq(transactionId))
                .fetchOne(adapter);
    }

    @Override
    public Transaction createTransaction(Transaction transaction, Configuration configuration) throws RuntimeException {
        transaction.setTransactionNumber(writeTransaction(transaction, configuration));
        return transaction;
    }


    /**
     * Write money transfer business-transaction in DB
     *
     * @param transaction   business-transaction object
     * @param configuration configuration DSL for transaction executing
     * @return new generated transaction UUID
     */
    private UUID writeTransaction(Transaction transaction, Configuration configuration) {
        return DSL.using(configuration).insertInto(TRANSACTIONS)
                .columns(
                        TRANSACTIONS.ACCOUNT_FROM_ID,
                        TRANSACTIONS.ACCOUNT_TO_ID,
                        TRANSACTIONS.AMOUNT,
                        TRANSACTIONS.STATUS)
                .values(
                        transaction.getFrom(),
                        transaction.getTo(),
                        transaction.getCount(),
                        transaction.getStatus().name())
                .returning(TRANSACTIONS.TRANSACTION_ID).fetchOne().get(TRANSACTIONS.TRANSACTION_ID);
    }

}
