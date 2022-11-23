package br.com.letscode.trabalho.service;

import br.com.letscode.trabalho.entity.CustomerPJ;
import br.com.letscode.trabalho.entity.InvestmentAccount;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;

import java.math.BigDecimal;
import java.math.RoundingMode;

class InvestmentAccountServicePJ implements FullAccountCycle<InvestmentAccount, CustomerPJ> {

    InvestmentAccount investmentAccount = null;

    @Override
    public InvestmentAccount openAccount(String customerDocument, DocumentType documentType) throws AccountException, CustomerException {
        Integer accountID = generateAccountId();

        investmentAccount = new InvestmentAccount();
        investmentAccount.setId(accountID);
        updateBalance(investmentAccount, new BigDecimal(0.00));

        return investmentAccount;
    }

    @Override
    public InvestmentAccount openAccount(CustomerPJ customerPJ, BigDecimal balanceValue) throws AccountException, CustomerException {
        Integer accountID = generateAccountId();

        investmentAccount = new InvestmentAccount();
        investmentAccount.setId(accountID);
        updateBalance(investmentAccount, balanceValue);

        return investmentAccount;
    }

    @Override
    public void deposit(InvestmentAccount account, BigDecimal depositValue) throws AccountException {
        applyIncome(account, depositValue);
    }

    @Override
    public void invest(InvestmentAccount account, BigDecimal investmentValue) throws AccountException {
        deposit(account, investmentValue);
    }

    @Override
    public void withdraw(InvestmentAccount account, BigDecimal withdrawValue) throws AccountException {
        BigDecimal newValue = applyFee(account, withdrawValue);
        BigDecimal newBalanceValue = account.getAccountBalance().subtract(withdrawValue).subtract(newValue);
        account.setAccountBalance(newBalanceValue);
    }

    @Override
    public void transfer(InvestmentAccount account, BigDecimal transferValue) throws AccountException {
        BigDecimal newValue = applyFee(account, transferValue);
        BigDecimal newBalanceValue = account.getAccountBalance().subtract(transferValue).subtract(newValue);
        account.setAccountBalance(newBalanceValue);
    }

    @Override
    public BigDecimal applyFee(InvestmentAccount account, BigDecimal depositValue) throws AccountException {
        BigDecimal fee = new BigDecimal(0.005);
        BigDecimal newValue = depositValue.multiply(fee);
        return newValue;
    }

    public void applyIncome(InvestmentAccount account, BigDecimal depositValue) throws AccountException {
        BigDecimal fee = new BigDecimal(0.035);
        BigDecimal newValue = depositValue.multiply(fee);
        BigDecimal newBalanceValue = account.getAccountBalance().add(depositValue).add(newValue);
        account.setAccountBalance(newBalanceValue.setScale(2, RoundingMode.UP));
    }
}