package br.com.letscode.trabalho.service;

import br.com.letscode.trabalho.entity.CustomerPJ;
import br.com.letscode.trabalho.entity.SavingsAccount;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;

import java.math.BigDecimal;
import java.math.RoundingMode;

class SavingsAccountServicePJ implements FullAccountCycle<SavingsAccount, CustomerPJ> {

    SavingsAccount savingsAccount = null;

    @Override
    public SavingsAccount openAccount(String customerDocument, DocumentType documentType) throws AccountException, CustomerException {
        throw new CustomerException("It's not a good class to work... try AccountService for Customer PF. Customer PJ can't have a Saving Account.");
    }

    @Override
    public SavingsAccount openAccount(CustomerPJ customerPJ, BigDecimal balanceValue) throws AccountException, CustomerException {
        throw new CustomerException("It's not a good class to work... try AccountService for Customer PF. Customer PJ can't have a Saving Account.");
    }

    @Override
    public void deposit(SavingsAccount account, BigDecimal depositValue) throws AccountException {
        applyIncome(account, depositValue);
    }

    @Override
    public void invest(SavingsAccount account, BigDecimal investmentValue) throws AccountException {
        deposit(account, investmentValue);
    }

    @Override
    public void withdraw(SavingsAccount account, BigDecimal withdrawValue) throws AccountException {
        BigDecimal newValue = applyFee(account, withdrawValue);
        BigDecimal newBalanceValue = account.getAccountBalance().subtract(withdrawValue).subtract(newValue);
        account.setAccountBalance(newBalanceValue);
    }

    @Override
    public void transfer(SavingsAccount account, BigDecimal transferValue) throws AccountException {
        BigDecimal newValue = applyFee(account, transferValue);
        BigDecimal newBalanceValue = account.getAccountBalance().subtract(transferValue).subtract(newValue);
        account.setAccountBalance(newBalanceValue);
    }

    @Override
    public BigDecimal applyFee(SavingsAccount account, BigDecimal depositValue) throws AccountException {
        BigDecimal fee = new BigDecimal(- 0.05);
        BigDecimal newValue = account.getAccountBalance().multiply(fee);
        return newValue;
    }

    public void applyIncome(SavingsAccount account, BigDecimal depositValue) throws AccountException {
        BigDecimal fee = new BigDecimal(0.01);
        BigDecimal newValue = depositValue.multiply(fee);
        BigDecimal newBalanceValue = account.getAccountBalance().add(depositValue).add(newValue);
        account.setAccountBalance(newBalanceValue.setScale(2, RoundingMode.UP));
    }
}