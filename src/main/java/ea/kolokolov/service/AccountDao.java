package ea.kolokolov.service;

import ea.kolokolov.model.User;

import java.math.BigDecimal;

public interface AccountDao {

    User getUserInfo(String id);

    String transfer(Integer from, Integer to, BigDecimal count);
}
