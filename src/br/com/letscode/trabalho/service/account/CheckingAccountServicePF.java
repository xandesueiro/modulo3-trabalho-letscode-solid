package br.com.letscode.trabalho.service.account;

import br.com.letscode.trabalho.entity.CheckingAccount;
import br.com.letscode.trabalho.entity.CustomerPF;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;

import java.math.BigDecimal;

class CheckingAccountServicePF
        implements AccountCycleOpen<CheckingAccount, CustomerPF>,
        AccountCyclePF<CheckingAccount, CustomerPF>{

    @Override
    public CheckingAccount openAccount(String customerDocument, DocumentType documentType) throws AccountException, CustomerException {
        Integer accountID = generateAccountId();

        CheckingAccount checkingAccount = new CheckingAccount();
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
    public CheckingAccount openAccount(CustomerPF customerPF, BigDecimal balanceValue) throws AccountException, CustomerException {
        CheckingAccount checkingAccount;
        Integer accountID = generateAccountId();

        checkingAccount = new CheckingAccount();
        checkingAccount.setId(accountID);
        updateBalance(checkingAccount, balanceValue);

        return checkingAccount;
    }

    @Override
    public void deposit(CheckingAccount account, BigDecimal depositValue) throws AccountException {
        account.getAccountBalance().add(depositValue);
    }

    @Override
    public void withdrawal(CheckingAccount checkingAccount, BigDecimal withdrawValue) throws AccountException {
        BigDecimal newBalanceValue = checkingAccount.getAccountBalance().subtract(withdrawValue);
        checkingAccount.setAccountBalance(newBalanceValue);
    }

    @Override
    public void transfer(CheckingAccount checkingAccount, BigDecimal transferValue) throws AccountException {
        BigDecimal newBalanceValue = checkingAccount.getAccountBalance().subtract(transferValue);
        checkingAccount.setAccountBalance(newBalanceValue);
    }
}
