package ea.kolokolov.service;

import ea.kolokolov.dao.AccountDao;
import ea.kolokolov.dao.TransactionDao;
import ea.kolokolov.model.Account;
import ea.kolokolov.model.Transaction;
import org.jooq.DSLContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;

import static ea.kolokolov.model.TransactionStatus.FAIL;
import static ea.kolokolov.model.TransactionStatus.SUCCESS;

@Singleton
public class TransactionServiceImpl implements TransactionService {

    private final TransactionDao transactionDao;
    private final AccountDao accountDao;
    private final DSLContext context;

    @Inject
    public TransactionServiceImpl(TransactionDao transactionDao, AccountDao accountDao, DSLContext context) {
        this.transactionDao = transactionDao;
        this.accountDao = accountDao;
        this.context = context;
    }

    @Override
    public List<Transaction> getAllTransactions(Integer accountId) {
        return transactionDao.getAllTransactions(accountId);
    }

    @Override
    public Transaction getTransaction(UUID transactionId) {
        return transactionDao.getTransaction(transactionId);
    }

    @Override
    public Transaction executeTransaction(Transaction transaction) {
        context.transaction(configuration -> {
            Account sourceAccount = accountDao.getAccount(transaction.getFrom(), configuration);
            Account distAccount = accountDao.getAccount(transaction.getTo(), configuration);

            if (sourceAccount != null && distAccount != null) {
                transaction.setStatus(
                        transferIsPossible(transaction, sourceAccount, distAccount) ?
                                SUCCESS : FAIL
                );

                sourceAccount.setBalance(sourceAccount.getBalance().subtract(transaction.getCount()));
                distAccount.setBalance(distAccount.getBalance().add(transaction.getCount()));

                accountDao.updateAccount(sourceAccount, configuration);
                accountDao.updateAccount(distAccount, configuration);
            } else {
                transaction.setStatus(FAIL);
            }
            transactionDao.createTransaction(transaction, configuration);
        });
        return transaction;
    }

    /**
     * Check all conditions for transfer money between account
     *
     * @param transaction transaction data
     * @param source      source Account
     * @param destination destination Account
     * @return true - if transfer is possible, else - false;
     */
    private boolean transferIsPossible(Transaction transaction, Account source, Account destination) {
        if (inputDataIsCorrect(transaction)) {
            return accountHaveEnoughBalance(transaction, source);
        }
        return false;
    }

    /**
     * Check available balance for transfer
     *
     * @param transaction business-transaction
     * @param account     checked account
     * @return true - if account have enough balance; false - else;
     */
    private boolean accountHaveEnoughBalance(Transaction transaction, Account account) {
        return account.getBalance().compareTo(transaction.getCount()) > 0;
    }

    /**
     * Check transaction correct form
     *
     * @param transaction business-transaction
     * @return true - if transaction is correct; else - false;
     */
    private boolean inputDataIsCorrect(Transaction transaction) {
        return !transaction.getFrom().equals(transaction.getTo());
    }

}
