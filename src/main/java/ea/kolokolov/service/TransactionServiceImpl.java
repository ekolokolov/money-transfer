package ea.kolokolov.service;

import ea.kolokolov.dao.TransactionDao;
import ea.kolokolov.model.Transaction;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;

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
    public Transaction getTransaction(UUID transactionId) {
        return transactionDao.getTransaction(transactionId);
    }

    @Override
    public Transaction transferMoney(Transaction transaction) {
        return transactionDao.transferMoney(transaction);
    }
}
