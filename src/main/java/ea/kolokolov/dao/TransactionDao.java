package ea.kolokolov.dao;

import ea.kolokolov.model.Transaction;

import java.util.List;

public interface TransactionDao {

    List<Transaction> getAllTransactions(Integer userId);

    Transaction getTransaction(Integer transactionId);

    Transaction createTransaction(Transaction transaction);

}
