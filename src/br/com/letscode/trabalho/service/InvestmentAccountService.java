package br.com.letscode.trabalho.service;

import br.com.letscode.trabalho.entity.CheckingAccount;
import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.entity.InvestimentAccount;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;

import java.math.BigDecimal;

class InvestmentAccountService implements AccountCycle<InvestimentAccount, Customer>{

    InvestimentAccount investimentAccount;

    @Override
    public InvestimentAccount openAccount(String customerDocument, DocumentType documentType) throws AccountException, CustomerException {
        Integer accountID = generateAccountId();

        investimentAccount = new InvestimentAccount();
        investimentAccount.setId(accountID);
        updateBalance(investimentAccount, new BigDecimal(0.00));

        return investimentAccount;
    }

    @Override
    public InvestimentAccount openAccount(Customer customer, BigDecimal balanceValue) throws AccountException, CustomerException {
        Integer accountID = generateAccountId();

        investimentAccount = new InvestimentAccount();
        investimentAccount.setId(accountID);
        updateBalance(investimentAccount, balanceValue);

        return investimentAccount;
    }

    @Override
    public void deposit(Customer customer, BigDecimal depositValue) throws AccountException {

    }

    @Override
    public void invest(Customer customer, BigDecimal investmentValue) throws AccountException {

    }

    @Override
    public void withdraw(Customer customer, BigDecimal investmentValue) throws AccountException {

    }

    @Override
    public void transfer(Customer customer, BigDecimal investmentValue) throws AccountException {

    }

}
