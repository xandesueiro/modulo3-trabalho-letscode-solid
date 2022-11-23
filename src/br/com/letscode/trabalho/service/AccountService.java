package br.com.letscode.trabalho.service;

import br.com.letscode.trabalho.entity.*;
import br.com.letscode.trabalho.enums.AccountType;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;
import br.com.letscode.trabalho.service.validation.AccountValidation;
import br.com.letscode.trabalho.utils.ConstantUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AccountService <T extends Account, U extends CustomerPF>{

    CustomerService customerService = new CustomerService();
    AccountCycle<CheckingAccount, CustomerPF> accountCycleCheckingAccount = new CheckingAccountService();
    AccountCycle<SavingsAccount, CustomerPF> accountCycleSavingsAccount = new SavingsAccountService();
    AccountCycle<InvestmentAccount, CustomerPF> accountCycleInvestmentAccount = new InvestmentAccountService();
    AccountValidation accountValidation = new AccountValidation();
    public U openAccount(String customerName, String customerDocument, DocumentType documentType) throws AccountException, CustomerException{
        if (!isCustomerPF(documentType)){
            throw new CustomerException("It's not a good class to work... try AccountServicePJ");
        }
        CheckingAccount checkingAccount;
        Customer customer;

        checkingAccount = accountCycleCheckingAccount.openAccount();

        customer = customerService.saveCustomer(customerName, customerDocument, documentType);
        customer.addAccount(checkingAccount);

        return (U) customer;
    }

    public U openAccount(U customer, AccountType accountType, BigDecimal balanceValue) throws AccountException, CustomerException{
        Account account;
        switch (accountType){
            case CHECKING_ACCOUNT -> {
                account = accountCycleCheckingAccount.openAccount(customer, balanceValue);
                customer.addAccount(account);
            }
            case SAVINGS_ACCOUNT -> {
                account = accountCycleSavingsAccount.openAccount(customer, balanceValue);
                customer.addAccount(account);
            }
            case INVESTMENT_ACCOUNT -> {
                account = accountCycleInvestmentAccount.openAccount(customer, balanceValue);
                customer.addAccount(account);
            }
            default -> throw new AccountException("Error: account type not defined");
        }
        return customer;
    }

    public void deposit(Account account, BigDecimal depositValue) throws AccountException{
        if (account instanceof CheckingAccount){
            accountCycleCheckingAccount.deposit((CheckingAccount) account, depositValue);
        }else if (account instanceof SavingsAccount){
            accountCycleSavingsAccount.deposit((SavingsAccount) account, depositValue);
        }else if (account instanceof InvestmentAccount) {
            accountCycleInvestmentAccount.deposit((InvestmentAccount) account, depositValue);
        }else{
            throw new AccountException("Error: account type not defined");
        }
    }

    public void invest(Account account, BigDecimal investmentValue) throws AccountException{
        if (account instanceof CheckingAccount){
            accountCycleCheckingAccount.invest((CheckingAccount) account, investmentValue);
        }else if (account instanceof SavingsAccount){
            accountCycleSavingsAccount.invest((SavingsAccount) account, investmentValue);
        }else if (account instanceof InvestmentAccount) {
            accountCycleInvestmentAccount.invest((InvestmentAccount) account, investmentValue);
        }else{
            throw new AccountException("Error: account type not defined");
        }
    }

    public void withdraw(Account account, BigDecimal withdrawValue) throws AccountException{

        validateBalance(account, withdrawValue);

        if (account instanceof CheckingAccount){
            accountCycleCheckingAccount.withdraw((CheckingAccount) account, withdrawValue);
        }else if (account instanceof SavingsAccount){
            accountCycleSavingsAccount.withdraw((SavingsAccount) account, withdrawValue);
        }else if (account instanceof InvestmentAccount) {
            accountCycleInvestmentAccount.withdraw((InvestmentAccount) account, withdrawValue);
        }else{
            throw new AccountException("Error: account type not defined");
        }
    }

    public void transfer(Account account, BigDecimal transferValue) throws AccountException{

        validateBalance(account, transferValue);

        if (account instanceof CheckingAccount){
            accountCycleCheckingAccount.transfer((CheckingAccount) account, transferValue);
        }else if (account instanceof SavingsAccount){
            accountCycleSavingsAccount.transfer((SavingsAccount) account, transferValue);
        }else if (account instanceof InvestmentAccount) {
            accountCycleInvestmentAccount.transfer((InvestmentAccount) account, transferValue);
        }else{
            throw new AccountException("Error: account type not defined");
        }
    }

    public BigDecimal checkBalance(T account){
        BigDecimal bdValanceValue;
        if (account.getAccountBalance() != null) {
            bdValanceValue = account.getAccountBalance().setScale(ConstantUtils.ACCOUNT_SCALE_BALANCE, RoundingMode.UP);
        }else{
            bdValanceValue = new BigDecimal(0.00);
        }

        return bdValanceValue;
    }

    private boolean isCustomerPF(DocumentType documentType){
        if (documentType.equals(DocumentType.CPF)) {
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
