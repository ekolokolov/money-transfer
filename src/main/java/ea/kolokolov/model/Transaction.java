package ea.kolokolov.model;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Object business-transaction
 * used for transfer money between two account
 */
public class Transaction {

    //Source account number
    private Integer from;

    //Destination account number
    private Integer to;

    //Amount money for transfer
    private BigDecimal count;

    //Transaction executing result
    private TransactionStatus status;

    //Unique generated value
    private UUID transactionNumber;

    public Transaction() {
    }

    public Transaction(Integer from, Integer to, BigDecimal count) {
        this.from = from;
        this.to = to;
        this.count = count;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public void setTransactionNumber(UUID transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public UUID getTransactionNumber() {
        return transactionNumber;
    }
}
