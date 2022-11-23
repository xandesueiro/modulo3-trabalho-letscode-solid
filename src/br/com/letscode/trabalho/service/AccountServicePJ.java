package br.com.letscode.trabalho.service;

import br.com.letscode.trabalho.entity.*;
import br.com.letscode.trabalho.enums.AccountType;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;
import br.com.letscode.trabalho.service.validation.AccountValidation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AccountServicePJ  <T extends Account, U extends CustomerPJ>{
    CustomerService customerService = new CustomerService();
    FullAccountCycle<CheckingAccount, CustomerPJ> fullAccountCycleCheckingAccount = new CheckingAccountServicePJ();
    FullAccountCycle<SavingsAccount, CustomerPJ> fullAccountCycleSavingsAccount = new SavingsAccountServicePJ();
    FullAccountCycle<InvestmentAccount, CustomerPJ> fullAccountCycleInvestmentAccount = new InvestmentAccountServicePJ();
    AccountValidation accountValidation = new AccountValidation();

    public U openAccount(String customerName, String customerDocument, DocumentType documentType) throws AccountException, CustomerException {
        if (!isCustomerPJ(documentType)){
            throw new CustomerException("It's not a good class to work... try AccountService for Customer PF");
        }
        CheckingAccount checkingAccount;
        Customer customer;

        checkingAccount =  fullAccountCycleCheckingAccount.openAccount(customerDocument, documentType);
        customer = customerService.saveCustomer(customerName, customerDocument, documentType);
        customer.addAccount(checkingAccount);

        return (U) customer;
    }

    public U openAccount(U customer, AccountType accountType, BigDecimal balanceValue) throws AccountException, CustomerException{
        Account account;
        switch (accountType){
            case CHECKING_ACCOUNT -> {
                account = fullAccountCycleCheckingAccount.openAccount(customer, balanceValue);
                customer.addAccount(account);
            }
            case SAVINGS_ACCOUNT -> {
                throw new CustomerException("A Customer PJ can't have a Savings Account");
            }
            case INVESTMENT_ACCOUNT -> {
                account = fullAccountCycleInvestmentAccount.openAccount(customer, balanceValue);
                customer.addAccount(account);
            }
            default -> throw new AccountException("Error: account type not defined");
        }
        return (U) customer;
    }

    public void deposit(T account, BigDecimal depositValue) throws AccountException{
        if (account instanceof CheckingAccount){
            fullAccountCycleCheckingAccount.deposit((CheckingAccount) account, depositValue);
        }else if (account instanceof SavingsAccount){
            fullAccountCycleSavingsAccount.deposit((SavingsAccount) account, depositValue);
        }else if (account instanceof InvestmentAccount) {
            fullAccountCycleInvestmentAccount.deposit((InvestmentAccount) account, depositValue);
        }else{
            throw new AccountException("Error: account type not defined");
        }
    }

    public void invest(T account, BigDecimal investmentValue) throws AccountException{
        if (account instanceof CheckingAccount){
            fullAccountCycleCheckingAccount.invest((CheckingAccount) account, investmentValue);
        }else if (account instanceof SavingsAccount){
            fullAccountCycleSavingsAccount.invest((SavingsAccount) account, investmentValue);
        }else if (account instanceof InvestmentAccount) {
            fullAccountCycleInvestmentAccount.invest((InvestmentAccount) account, investmentValue);
        }else{
            throw new AccountException("Error: account type not defined");
        }
    }

    public void withdraw(T account, BigDecimal withdrawValue) throws AccountException{
        validateBalance(account, withdrawValue);

        if (account instanceof CheckingAccount){
            fullAccountCycleCheckingAccount.withdraw((CheckingAccount) account, withdrawValue);
        }else if (account instanceof SavingsAccount){
            fullAccountCycleSavingsAccount.withdraw((SavingsAccount) account, withdrawValue);
        }else if (account instanceof InvestmentAccount) {
            fullAccountCycleInvestmentAccount.withdraw((InvestmentAccount) account, withdrawValue);
        }else{
            throw new AccountException("Error: account type not defined");
        }
    }

    public void transfer(T account, BigDecimal transferValue) throws AccountException{
        validateBalance(account, transferValue);

        if (account instanceof CheckingAccount){
            fullAccountCycleCheckingAccount.transfer((CheckingAccount) account, transferValue);
        }else if (account instanceof SavingsAccount){
            fullAccountCycleSavingsAccount.transfer((SavingsAccount) account, transferValue);
        }else if (account instanceof InvestmentAccount) {
            fullAccountCycleInvestmentAccount.transfer((InvestmentAccount) account, transferValue);
        }else{
            throw new AccountException("Error: account type not defined");
        }
    }

    public BigDecimal checkBalance(T account){
        BigDecimal bdValanceValue;
        if (account.getAccountBalance() != null) {
            bdValanceValue = account.getAccountBalance().setScale(Account.SCALE_BALANCE, RoundingMode.UP);
        }else{
            bdValanceValue = new BigDecimal(0.00);
        }

        return bdValanceValue;
    }

    private boolean isCustomerPJ(DocumentType documentType){
        if (documentType.equals(DocumentType.CNPJ)) {
            return true;
        }
        else {
            return false;
        }
    }

    private void validateBalance(Account account, BigDecimal value) throws AccountException{
        if (!accountValidation.isBalancePositive(account)){
            throw new AccountException("Sorry, Your balance is negative and this transaction cannot be executed in your account "
                    + account.getAccountLabel()
                    + " : "
                    + account.getId()
                    + ".");
        }
        if (!accountValidation.hasAvailableBalance(account, value)){
            throw new AccountException("Sorry, there is no available balance in your account "
                    + account.getAccountLabel()
                    + " : "
                    + account.getId()
                    + " for this transaction.");
        }
    }
}
