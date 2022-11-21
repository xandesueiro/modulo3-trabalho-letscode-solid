package br.com.letscode.trabalho.service;

import br.com.letscode.trabalho.entity.*;
import br.com.letscode.trabalho.enums.AccountType;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Objects;

public class AccountService <T extends Account, U extends Customer>{

    CustomerService customerService = new CustomerService();

    public U openAccount(String customerName, String customerDocument, DocumentType documentType) throws AccountException, CustomerException{

        CheckingAccount checkingAccount = null;
        Customer customer = null;

        switch (documentType){
            case CPF -> {
                AccountCycle accountCycle = new CheckingAccountService();
                checkingAccount = (CheckingAccount) accountCycle.openAccount(customerDocument, documentType);

                customer = customerService.saveCustomer(customerName, customerDocument, documentType);
                customer.addAccount(checkingAccount);

            }
            case CNPJ -> {

            }
            default -> throw new AccountException("Error: document type not defined");
        }

        return (U) customer;
    }

    public U openAccount(U customer, AccountType accountType, BigDecimal balanceValue) throws AccountException, CustomerException{

        Account account= null;
        switch (accountType){
            case CHECKING_ACCOUNT -> {
                AccountCycle<CheckingAccount, Customer> accountCycle = new CheckingAccountService();
                account = accountCycle.openAccount(customer, balanceValue);

                customer.addAccount(account);

            }
            case SAVINGS_ACCOUNT -> {
                AccountCycle<SavingsAccount, Customer> accountCycle = new SavingsAccountService();
                account = accountCycle.openAccount(customer, balanceValue);

                customer.addAccount(account);
            }
            case INVESTIMENT_ACCOUNT -> {
                AccountCycle accountCycle = new InvestmentAccountService();
                account = accountCycle.openAccount(customer, balanceValue);

                customer.addAccount(account);
            }
            default -> throw new AccountException("Error: account type not defined");

        }

        return (U) customer;
    }

    public void deposit(U customer, BigDecimal depositValue) throws AccountException{

    }

    public void invest(U customer, BigDecimal investmentValue) throws AccountException{

    }

    public void withdraw(U customer, BigDecimal investmentValue) throws AccountException{

    }

    public void transfer(U customer, BigDecimal investmentValue) throws AccountException{

    }
    public void printAccountStatement(U customer){
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("------------------------------------------ Resumo: Contas do cliente  ------------------------------------------\n");
        sb.append("------------------------------------------ Summary: Customer account  ------------------------------------------\n");
        sb.append("Customer name         : ").append(customer.getName()).append("\n");

        for (Map.Entry<Integer, Account> accounts : customer.getAccounts().entrySet()) {
            Account varAccount = accounts.getValue();
            sb.append(" ---> Account ID      : ").append(varAccount.getId()).append("\n");
            sb.append(" ---> Account Type    : ").append(varAccount.getAccountLabel()).append("\n");
            sb.append(" ---> Account Balance : ").append(checkFormattedBalanceInLocalCurrency((T) varAccount)).append("\n");
            sb.append("\n");
        }
        sb.append("\n");

        System.out.println(sb);
    }

    private BigDecimal checkBalance(T account){
        BigDecimal bdValanceValue;
        if (account.getAccountBalance() != null) {
            bdValanceValue = account.getAccountBalance().setScale(Account.SCALE_BALANCE, RoundingMode.UP);
        }else{
            bdValanceValue = new BigDecimal(0.00);
        }

        return bdValanceValue;
    }

    private String checkFormattedBalanceInLocalCurrency(T account){
        String bdValanceValue;
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        bdValanceValue = nf.format(Objects.requireNonNullElseGet(checkBalance(account), () -> new BigDecimal(0.00)));

        return bdValanceValue;
    }

}
