package ea.kolokolov.dao;

import ea.kolokolov.adapter.TransactionAdapter;
import ea.kolokolov.model.Transaction;
import ea.kolokolov.model.TransactionStatus;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static ea.kolokolov.jooq.tables.Account.ACCOUNT;
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
    public Transaction transferMoney(Transaction transaction) {
        context.transaction(configuration -> {
            try {
                if (!correctDestination(transaction) || !haveEnoughMoney(transaction, configuration)) {
                    transaction.setStatus(TransactionStatus.FAIL);
                } else {
                    transaction.setStatus(TransactionStatus.SUCCESS);
                }
                transaction.setTransactionNumber(writeTransaction(transaction, configuration));
            } catch (RuntimeException e) {
                throw e;
            }
        });
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

    /**
     * Compare source and destination account numbers
     *
     * @param transaction business-transaction
     * @return true - if source and destination have different value, else - false.
     */
    private boolean correctDestination(Transaction transaction) {
        return !transaction.getFrom().equals(transaction.getTo());
    }

    /**
     * Compare current account balance with current business-transaction amount value
     *
     * @param transaction   business-transaction
     * @param configuration configuration DSL for transaction executing
     * @return true - if account balance >= amount from business transaction, false - else.
     */
    private boolean haveEnoughMoney(Transaction transaction, Configuration configuration) {
        BigDecimal balance = DSL.using(configuration)
                .select(ACCOUNT.BALANCE)
                .from(ACCOUNT)
                .where(ACCOUNT.ACCOUNT_NUMBER.eq(transaction.getFrom()))
                .fetchOneInto(BigDecimal.class);
        return balance.compareTo(transaction.getCount()) >= 0;
    }
}
