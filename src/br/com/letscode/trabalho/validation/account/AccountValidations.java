package br.com.letscode.trabalho.validation.account;

import br.com.letscode.trabalho.entity.Account;
import br.com.letscode.trabalho.exception.AccountException;

import java.math.BigDecimal;

public interface AccountValidations<T extends Account> {
    void validate(T account, BigDecimal value) throws AccountException;
}
