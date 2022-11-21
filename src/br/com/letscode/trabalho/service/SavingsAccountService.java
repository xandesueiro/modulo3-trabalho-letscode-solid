package br.com.letscode.trabalho.service;


import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.entity.CustomerPF;
import br.com.letscode.trabalho.entity.SavingsAccount;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;

import java.math.BigDecimal;

class SavingsAccountService implements AccountCycle<SavingsAccount, CustomerPF>{

    SavingsAccount savingsAccount;
    @Override
    public SavingsAccount openAccount(String customerDocument, DocumentType documentType) throws AccountException, CustomerException {
        if (documentType.equals(DocumentType.CPF)) {
            Integer accountID = generateAccountId();

            savingsAccount = new SavingsAccount();
            savingsAccount.setId(accountID);
            updateBalance(savingsAccount, new BigDecimal(0.00));
        }else {
            throw new CustomerException("A Customer PJ can't have a Savings Account");
        }

        return savingsAccount;
    }

    @Override
    public SavingsAccount openAccount(CustomerPF customer, BigDecimal balanceValue) throws CustomerException {
        if (customer instanceof CustomerPF) {
            Integer accountID = generateAccountId();

            savingsAccount = new SavingsAccount();
            savingsAccount.setId(accountID);
            updateBalance(savingsAccount, balanceValue);
        }else {
            throw new CustomerException("A Customer PJ can't have a Savings Account");
        }

        return savingsAccount;
    }

    @Override
    public void deposit(CustomerPF customer, BigDecimal depositValue) throws AccountException {

    }

    @Override
    public void invest(CustomerPF customer, BigDecimal investmentValue) throws AccountException {

    }

    @Override
    public void withdraw(CustomerPF customer, BigDecimal investmentValue) throws AccountException {

    }

    @Override
    public void transfer(CustomerPF customer, BigDecimal investmentValue) throws AccountException {

    }

}
