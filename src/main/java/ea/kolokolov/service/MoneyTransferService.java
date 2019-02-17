package ea.kolokolov.service;

import java.math.BigDecimal;

public interface MoneyTransferService {

    String transfer(Integer from, Integer to, BigDecimal count);
}
