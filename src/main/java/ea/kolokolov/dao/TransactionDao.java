package ea.kolokolov.dao;

import ea.kolokolov.model.Transaction;

import java.util.List;
import java.util.UUID;

/**
 * Data Access layer for Business Money Transactions
 * Allow read/write transactions in DB
 * <p>
 * transferMoney do business logic in DA layer because need do it
 * in one DB transaction.
 */
public interface TransactionDao {


    /**
     * Get all transactions for user account
     * Not check user permissions!
     *
     * @param accountId identifier for account.
     * @return @{@link List} of @{@link Transaction}
     * if account not have transactions return empty @{@link List}
     */
    List<Transaction> getAllTransactions(Integer accountId);

    /**
     * Get transaction with concrete UUID.
     * Not check user permissions!
     *
     * @param transactionId @{@link UUID}
     * @return one @{@link Transaction}
     * if transaction not exist - return null
     */
    Transaction getTransaction(UUID transactionId);

    /**
     * Transfer money from account transaction.from and try move on transaction.to
     * Write new business-transactions in db.
     * Save status @{@link ea.kolokolov.model.TransactionStatus} for transaction
     * if transaction complete successfully save with status "SUCCESS"
     * else save with "FAIL"
     *
     * @param transaction business-transaction
     * @return new creation transaction;
     * <p>
     * May produce RuntimeException if can't save transaction in DB.
     */
    Transaction transferMoney(Transaction transaction) throws RuntimeException;

}
