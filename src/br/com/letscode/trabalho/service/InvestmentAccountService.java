package br.com.letscode.trabalho.service;

import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.entity.InvestimentAccount;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;

import java.math.BigDecimal;

public class InvestmentAccountService implements AccountCycle<InvestimentAccount, Customer>{

    @Override
    public InvestimentAccount openAccount(String customerDocument, DocumentType documentType) throws AccountException, CustomerException {
        return null;
    }

    @Override
    public InvestimentAccount openAccount(Customer customer, DocumentType documentType, BigDecimal balanceValue) throws AccountException, CustomerException {
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
