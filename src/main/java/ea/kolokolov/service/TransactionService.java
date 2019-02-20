package ea.kolokolov.service;

import ea.kolokolov.model.Transaction;

import java.util.List;
import java.util.UUID;

/**
 * Service for business methods with transactions
 */
public interface TransactionService {

    /**
     * Return all transactions for account
     *
     * @param accountId unique account identifier
     * @return List Transaction or empty List
     */
    List<Transaction> getAllTransactions(Integer accountId);

    /**
     * Return one transaction with concrete UUID
     *
     * @param transactionId {@link UUID}
     * @return Transaction or null
     */
    Transaction getTransaction(UUID transactionId);

    /**
     * Transfer money between to accounts
     *
     * @param transaction business-transaction object
     * @return created transaction
     */
    Transaction transferMoney(Transaction transaction);
}
