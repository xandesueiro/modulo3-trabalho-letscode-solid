package br.com.letscode.trabalho.service;

import br.com.letscode.trabalho.entity.CheckingAccount;
import br.com.letscode.trabalho.entity.CustomerPF;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;

import java.math.BigDecimal;

class CheckingAccountService implements AccountCycle<CheckingAccount, CustomerPF>{
    @Override
    public CheckingAccount openAccount() throws AccountException, CustomerException {
        CheckingAccount checkingAccount;
        Integer accountID = generateAccountId();

        checkingAccount = new CheckingAccount();
        checkingAccount.setId(accountID);
        updateBalance(checkingAccount, new BigDecimal(0.00));

        return checkingAccount;
    }

    @Override
    public CheckingAccount openAccount(CustomerPF customerPF, BigDecimal balanceValue) throws AccountException, CustomerException {
        CheckingAccount checkingAccount;
        Integer accountID = generateAccountId();

        checkingAccount = new CheckingAccount();
        checkingAccount.setId(accountID);
        updateBalance(checkingAccount, balanceValue);

        return checkingAccount;
    }

    @Override
    public void deposit(CheckingAccount checkingAccount, BigDecimal depositValue) throws AccountException {
        applyIncome(checkingAccount, depositValue);
    }

    @Override
    public void invest(CheckingAccount checkingAccount, BigDecimal investmentValue) throws AccountException {
        deposit(checkingAccount, investmentValue);
    }

    @Override
    public void withdraw(CheckingAccount checkingAccount, BigDecimal withdrawValue) throws AccountException {
        BigDecimal newBalanceValue = checkingAccount.getAccountBalance().subtract(withdrawValue);
        checkingAccount.setAccountBalance(newBalanceValue);
    }

    @Override
    public void transfer(CheckingAccount checkingAccount, BigDecimal transferValue) throws AccountException {
        BigDecimal newBalanceValue = checkingAccount.getAccountBalance().subtract(transferValue);
        checkingAccount.setAccountBalance(newBalanceValue);
    }

    public void applyIncome(CheckingAccount account, BigDecimal depositValue) throws AccountException {
        BigDecimal newValue = account.getAccountBalance().add(depositValue);
        account.setAccountBalance(newValue);
    }
}
