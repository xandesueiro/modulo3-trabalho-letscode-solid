package br.com.letscode.trabalho.service.account;

import br.com.letscode.trabalho.entity.Account;
import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;

import java.math.BigDecimal;

public interface AccountCycleOpen<T extends Account, U extends Customer> {
    T openAccount(String customerDocument, DocumentType documentType) throws AccountException, CustomerException;
    T openAccount(U customerPJ, BigDecimal balanceValue) throws AccountException, CustomerException;
    T openAccount() throws AccountException, CustomerException;
}
