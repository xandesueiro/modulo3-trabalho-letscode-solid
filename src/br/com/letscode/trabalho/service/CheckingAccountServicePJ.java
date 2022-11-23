package br.com.letscode.trabalho.service;

import br.com.letscode.trabalho.entity.CheckingAccount;
import br.com.letscode.trabalho.entity.CustomerPJ;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;

import java.math.BigDecimal;

class CheckingAccountServicePJ implements FullAccountCycle<CheckingAccount, CustomerPJ> {

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
    public CheckingAccount openAccount(CustomerPJ customerPJ, BigDecimal balanceValue) throws AccountException, CustomerException {
        Integer accountID = generateAccountId();

        checkingAccount = new CheckingAccount();
        checkingAccount.setId(accountID);
        updateBalance(checkingAccount, balanceValue);

        return checkingAccount;
    }

    @Override
    public void deposit(CheckingAccount account, BigDecimal depositValue) throws AccountException {
        applyIncome(account, depositValue);
    }

    @Override
    public void invest(CheckingAccount account, BigDecimal investmentValue) throws AccountException {
        deposit(account, investmentValue);
    }

    @Override
    public void withdraw(CheckingAccount account, BigDecimal withdrawValue) throws AccountException {
        BigDecimal newValue = applyFee(account, withdrawValue);
        BigDecimal newBalanceValue = account.getAccountBalance().subtract(withdrawValue).subtract(newValue);
        account.setAccountBalance(newBalanceValue);
    }

    @Override
    public void transfer(CheckingAccount account, BigDecimal transferValue) throws AccountException {
        BigDecimal newValue = applyFee(account, transferValue);
        BigDecimal newBalanceValue = account.getAccountBalance().subtract(transferValue).subtract(newValue);
        account.setAccountBalance(newBalanceValue);
    }

    @Override
    public BigDecimal applyFee(CheckingAccount account, BigDecimal depositValue) throws AccountException {
        BigDecimal fee = new BigDecimal(0.005);
        BigDecimal newValue = account.getAccountBalance().multiply(fee);
        return newValue;
    }

    public void applyIncome(CheckingAccount account, BigDecimal depositValue) throws AccountException {
        BigDecimal newValue = account.getAccountBalance().add(depositValue);
        account.setAccountBalance(newValue);
    }
}