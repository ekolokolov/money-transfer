package ea.kolokolov.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Transaction {

    private Integer from;

    private Integer to;

    private BigDecimal count;

    private TransactionStatus status;

    private UUID transactionNumber;

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
