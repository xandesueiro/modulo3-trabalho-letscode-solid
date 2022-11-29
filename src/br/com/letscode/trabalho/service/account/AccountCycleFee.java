package br.com.letscode.trabalho.service.account;

import br.com.letscode.trabalho.entity.Account;
import br.com.letscode.trabalho.exception.AccountException;

import java.math.BigDecimal;

public interface AccountCycleFee<T extends Account> {
    BigDecimal applyFee(T account, BigDecimal depositValue) throws AccountException;
}
