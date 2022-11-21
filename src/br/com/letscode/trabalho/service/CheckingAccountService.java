package br.com.letscode.trabalho.service;

import br.com.letscode.trabalho.entity.CheckingAccount;
import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;

import java.math.BigDecimal;

class CheckingAccountService implements AccountCycle<CheckingAccount, Customer>{

    CheckingAccount checkingAccount = null;

    @Override
    public CheckingAccount openAccount(String customerDocument, DocumentType documentType) throws AccountException, CustomerException {

        Integer accountID = generateAccountId();

        checkingAccount = new CheckingAccount();
        checkingAccount.setId(accountID);
        updateBalance(checkingAccount, new BigDecimal(0.00));

        return checkingAccount;
    }

    @Override
    public CheckingAccount openAccount(Customer customer, BigDecimal balanceValue) throws AccountException, CustomerException {
        Integer accountID = generateAccountId();

        checkingAccount = new CheckingAccount();
        checkingAccount.setId(accountID);
        updateBalance(checkingAccount, balanceValue);

        return checkingAccount;
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
