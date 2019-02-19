package ea.kolokolov.service;

import ea.kolokolov.model.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getAllTransactions(Integer userId);

    Transaction getTransactionById(Integer userId, Integer transactionId);

    Transaction createTransaction(Transaction transaction);
}
