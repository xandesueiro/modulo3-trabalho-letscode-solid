package br.com.letscode.trabalho.service.account;

import br.com.letscode.trabalho.entity.CustomerPF;
import br.com.letscode.trabalho.entity.SavingsAccount;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;

import java.math.BigDecimal;
import java.math.RoundingMode;

class SavingsAccountServicePF
        implements AccountCycleOpen<SavingsAccount, CustomerPF>,
        AccountCyclePF<SavingsAccount, CustomerPF>,
        AccountCycleIncome<SavingsAccount>
{

    public SavingsAccount openAccount(String customerDocument, DocumentType documentType) throws AccountException, CustomerException {
        Integer accountID = generateAccountId();

        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setId(accountID);
        updateBalance(savingsAccount, new BigDecimal(0.00));

        return savingsAccount;
    }

    @Override
    public SavingsAccount openAccount() throws AccountException, CustomerException {
        SavingsAccount savingsAccount;
        Integer accountID = generateAccountId();

        savingsAccount = new SavingsAccount();
        savingsAccount.setId(accountID);
        updateBalance(savingsAccount, new BigDecimal(0.00));

        return savingsAccount;
    }
    @Override
    public SavingsAccount openAccount(CustomerPF customer, BigDecimal balanceValue) throws CustomerException {
        Integer accountID = generateAccountId();

        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setId(accountID);
        updateBalance(savingsAccount, balanceValue);

        return savingsAccount;
    }

    @Override
    public void deposit(SavingsAccount savingsAccount, BigDecimal depositValue) throws AccountException {
        applyIncome(savingsAccount, depositValue);
    }

    @Override
    public void withdrawal(SavingsAccount savingsAccount, BigDecimal withdrawValue) throws AccountException {
        BigDecimal newBalanceValue = savingsAccount.getAccountBalance().subtract(withdrawValue);
        savingsAccount.setAccountBalance(newBalanceValue);
    }

    @Override
    public void transfer(SavingsAccount savingsAccount, BigDecimal transferValue) throws AccountException {
        BigDecimal newBalanceValue = savingsAccount.getAccountBalance().subtract(transferValue);
        savingsAccount.setAccountBalance(newBalanceValue);
    }

    @Override
    public void invest(SavingsAccount savingsAccount, BigDecimal investmentValue) throws AccountException {
        deposit(savingsAccount, investmentValue);
    }

    @Override
    public void applyIncome(SavingsAccount savingsAccount, BigDecimal depositValue) throws AccountException {
        BigDecimal fee = new BigDecimal(0.01);
        BigDecimal newValue = depositValue.multiply(fee);
        BigDecimal newBalanceValue = savingsAccount.getAccountBalance().add(depositValue).add(newValue);
        savingsAccount.setAccountBalance(newBalanceValue.setScale(2, RoundingMode.UP));
    }
}
