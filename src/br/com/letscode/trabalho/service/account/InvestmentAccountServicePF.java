package br.com.letscode.trabalho.service.account;

import br.com.letscode.trabalho.entity.CustomerPF;
import br.com.letscode.trabalho.entity.InvestmentAccount;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;

import java.math.BigDecimal;
import java.math.RoundingMode;

class InvestmentAccountServicePF implements AccountCyclePF<InvestmentAccount, CustomerPF> {

    InvestmentAccount investmentAccount;

    @Override
    public InvestmentAccount openAccount() throws AccountException, CustomerException {
        Integer accountID = generateAccountId();

        investmentAccount = new InvestmentAccount();
        investmentAccount.setId(accountID);
        updateBalance(investmentAccount, new BigDecimal(0.00));

        return investmentAccount;
    }

    @Override
    public InvestmentAccount openAccount(CustomerPF customer, BigDecimal balanceValue) throws AccountException, CustomerException {
        Integer accountID = generateAccountId();

        investmentAccount = new InvestmentAccount();
        investmentAccount.setId(accountID);
        updateBalance(investmentAccount, balanceValue);

        return investmentAccount;
    }

    @Override
    public void deposit(InvestmentAccount investmentAccount, BigDecimal depositValue) throws AccountException {
        applyIncome(investmentAccount, depositValue);
    }

    @Override
    public void invest(InvestmentAccount investmentAccount, BigDecimal investmentValue) throws AccountException {
        deposit(investmentAccount, investmentValue);
    }

    @Override
    public void withdrawal(InvestmentAccount investmentAccount, BigDecimal withdrawValue) throws AccountException {
        BigDecimal newBalanceValue = investmentAccount.getAccountBalance().subtract(withdrawValue);
        investmentAccount.setAccountBalance(newBalanceValue);
    }

    @Override
    public void transfer(InvestmentAccount investmentAccount, BigDecimal transferValue) throws AccountException {
        BigDecimal newBalanceValue = investmentAccount.getAccountBalance().subtract(transferValue);
        investmentAccount.setAccountBalance(newBalanceValue);
    }

    public void applyIncome(InvestmentAccount account, BigDecimal depositValue) throws AccountException {
        BigDecimal fee = new BigDecimal(0.015);
        BigDecimal newValue = depositValue.multiply(fee);
        BigDecimal newBalanceValue = account.getAccountBalance().add(depositValue).add(newValue);
        account.setAccountBalance(newBalanceValue.setScale(2, RoundingMode.UP));
    }
}
