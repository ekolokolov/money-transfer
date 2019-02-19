package ea.kolokolov.adapter;

import ea.kolokolov.model.Transaction;
import ea.kolokolov.model.TransactionStatus;
import org.jooq.Record;
import org.jooq.RecordMapper;

import static ea.kolokolov.jooq.tables.Transactions.TRANSACTIONS;

public class TransactionAdapter implements RecordMapper<Record, Transaction> {

    private StatusConverter statusConverter = new StatusConverter();

    @Override
    public Transaction map(Record record) {
        Transaction transaction = new Transaction();
        transaction.setFrom(record.get(TRANSACTIONS.ACCOUNT_FROM_ID));
        transaction.setTo(record.get(TRANSACTIONS.ACCOUNT_TO_ID));
        transaction.setTransactionNumber(record.get(TRANSACTIONS.TRANSACTION_ID));
        transaction.setStatus(record.get(TRANSACTIONS.STATUS, statusConverter));
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setCount(record.get(TRANSACTIONS.AMOUNT));
        return transaction;
    }
}
