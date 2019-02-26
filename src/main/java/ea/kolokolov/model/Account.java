package ea.kolokolov.model;

import com.owlike.genson.annotation.JsonIgnore;

import java.math.BigDecimal;

/**
 * Bank Account for user.
 */
public class Account {

    private Integer id;

    private Integer userId;

    //Business identifier
    private Integer accountId;

    //Current amount money on account
    private BigDecimal balance;

    public Account() {
    }

    public Account(Integer userId, Integer accountId, BigDecimal balance) {
        this.userId = userId;
        this.accountId = accountId;
        this.balance = balance;
    }

    @JsonIgnore
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @JsonIgnore
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
