package ea.kolokolov.service;

import ea.kolokolov.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    List<Transaction> getAllTransactions(Integer userId);

    Transaction getTransaction(UUID transactionId);

    Transaction createTransaction(Transaction transaction);
}
