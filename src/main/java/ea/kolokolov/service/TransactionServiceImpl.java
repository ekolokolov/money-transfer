package ea.kolokolov.service;

import ea.kolokolov.dao.TransactionDao;
import ea.kolokolov.model.Transaction;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class TransactionServiceImpl implements TransactionService {

    private TransactionDao transactionDao;

    @Inject
    public TransactionServiceImpl(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    public List<Transaction> getAllTransactions(Integer userId) {
        return transactionDao.getAllTransactions(userId);
    }

    @Override
    public Transaction getTransactionById(Integer userId, Integer transactionId) {
        return null;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionDao.createTransaction(transaction);
    }
}
