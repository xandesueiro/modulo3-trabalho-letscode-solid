package br.com.letscode.trabalho.service.account;

import br.com.letscode.trabalho.entity.Account;
import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.exception.AccountException;

import java.math.BigDecimal;

public interface AccountCycle<T extends Account, U extends Customer> {
    void deposit(T account, BigDecimal depositValue) throws AccountException;
    void withdrawal(T account, BigDecimal withdrawValue) throws AccountException;
    void transfer(T account, BigDecimal transferValue) throws AccountException;

}
