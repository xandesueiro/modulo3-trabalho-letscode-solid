package br.com.letscode.trabalho.service.account;

import br.com.letscode.trabalho.entity.CheckingAccount;
import br.com.letscode.trabalho.entity.CustomerPJ;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;

import java.math.BigDecimal;

class CheckingAccountServicePJ
        implements AccountCyclePJ<CheckingAccount, CustomerPJ>,
        AccountCycleOpen<CheckingAccount, CustomerPJ>,
        AccountCycleFee<CheckingAccount>
{


    @Override
    public CheckingAccount openAccount(String customerDocument, DocumentType documentType) throws AccountException, CustomerException {
        CheckingAccount checkingAccount;
        Integer accountID = generateAccountId();

        checkingAccount = new CheckingAccount();
        checkingAccount.setId(accountID);
        updateBalance(checkingAccount, new BigDecimal(0.00));

        return checkingAccount;
    }

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
    public CheckingAccount openAccount(CustomerPJ customerPJ, BigDecimal balanceValue) throws AccountException, CustomerException {
        CheckingAccount checkingAccount;
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
    public void withdrawal(CheckingAccount account, BigDecimal withdrawValue) throws AccountException {
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