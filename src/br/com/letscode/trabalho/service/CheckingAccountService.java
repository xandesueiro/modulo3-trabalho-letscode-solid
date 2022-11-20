package br.com.letscode.trabalho.service;

import br.com.letscode.trabalho.entity.CheckingAccount;
import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;

import java.math.BigDecimal;

public class CheckingAccountService implements AccountCycle<CheckingAccount, Customer>{


    @Override
    public CheckingAccount openAccount(String customerDocument, DocumentType documentType) throws AccountException, CustomerException {
        return null;
    }

    @Override
    public CheckingAccount openAccount(Customer customer, DocumentType documentType, BigDecimal balanceValue) throws AccountException, CustomerException {
        return null;
    }

    @Override
    public void deposit(Customer customer, BigDecimal depositValue) throws AccountException {

    }

    @Override
    public void investment(Customer customer, BigDecimal investmentValue) throws AccountException {

    }

    @Override
    public void withdraw(Customer customer, BigDecimal investmentValue) throws AccountException {

    }

    @Override
    public void transfer(Customer customer, BigDecimal investmentValue) throws AccountException {

    }

}
