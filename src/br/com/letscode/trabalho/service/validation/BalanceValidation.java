package br.com.letscode.trabalho.service.validation;

import br.com.letscode.trabalho.entity.Account;

import java.math.BigDecimal;

public interface BalanceValidation<T extends Account> {

    boolean isBalancePositive(T account);
    boolean hasAvailableBalance(T account, BigDecimal value);
}
