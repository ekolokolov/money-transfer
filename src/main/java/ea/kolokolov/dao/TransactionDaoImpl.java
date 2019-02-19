package ea.kolokolov.dao;

import ea.kolokolov.adapter.TransactionAdapter;
import ea.kolokolov.exception.OperationForbiddenException;
import ea.kolokolov.jooq.tables.records.TransactionsRecord;
import ea.kolokolov.model.Account;
import ea.kolokolov.model.Transaction;
import ea.kolokolov.model.TransactionStatus;
import org.jooq.DSLContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;

import static ea.kolokolov.jooq.tables.Transactions.TRANSACTIONS;

@Singleton
public class TransactionDaoImpl implements TransactionDao {

    private final TransactionAdapter adapter = new TransactionAdapter();

    private AccountDao accountDao;
    private DSLContext context;

    @Inject
    public TransactionDaoImpl(AccountDao accountDao, DSLContext context) {
        this.accountDao = accountDao;
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
    public Transaction createTransaction(Transaction transaction) {
        context.transaction(configuration -> {
            Account account = accountDao.getAccount(transaction.getFrom());
            if (account.getBalance().compareTo(transaction.getCount()) < 0) {
                throw new OperationForbiddenException("User don't have enough money");
            }
            try {

                TransactionsRecord transactionsRecord = context.insertInto(TRANSACTIONS)
                        .columns(
                                TRANSACTIONS.ACCOUNT_FROM_ID,
                                TRANSACTIONS.ACCOUNT_TO_ID,
                                TRANSACTIONS.AMOUNT,
                                TRANSACTIONS.STATUS)
                        .values(
                                transaction.getFrom(),
                                transaction.getTo(),
                                transaction.getCount(),
                                TransactionStatus.SUCCESS.name())
                        .returning(TRANSACTIONS.ID, TRANSACTIONS.TRANSACTION_ID).fetchOne();
            } catch (RuntimeException e) {
                transaction.setStatus(TransactionStatus.FAIL);
                throw new OperationForbiddenException("User don't have enough money");
            }
        });
        return transaction;
    }
}
