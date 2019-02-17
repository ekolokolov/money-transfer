package ea.kolokolov.service;

import ea.kolokolov.model.User;

import java.math.BigDecimal;

public interface AccountDao {

    User getUserInfo(Integer id);

    String transfer(Integer from, Integer to, BigDecimal count);
}
