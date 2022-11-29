package br.com.letscode.trabalho.service.account;

import br.com.letscode.trabalho.entity.Account;
import br.com.letscode.trabalho.exception.AccountException;

import java.math.BigDecimal;

public interface AccountCycleIncome<T extends Account> {
    void invest(T account, BigDecimal investmentValue) throws AccountException;
    void applyIncome(T account, BigDecimal incomeValue) throws AccountException;
}
