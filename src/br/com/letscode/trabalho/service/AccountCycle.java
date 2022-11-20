package br.com.letscode.trabalho.service;

import br.com.letscode.trabalho.entity.Account;
import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public interface AccountCycle<T extends Account, U extends Customer>{

    T openAccount(String customerDocument, DocumentType documentType) throws AccountException, CustomerException;
    T openAccount(U customer, DocumentType documentType, BigDecimal balanceValue) throws AccountException, CustomerException;
    void deposit(U customer, BigDecimal depositValue) throws AccountException;
    void investment(U customer, BigDecimal investmentValue) throws AccountException;
    void withdraw(U customer, BigDecimal investmentValue) throws AccountException;
    void transfer(U customer, BigDecimal investmentValue) throws AccountException;

    default Integer generateAccountId(){
        Random random = new Random();
        return random.nextInt(Account.RANGE_START, Account.RANGE_END);
    }

    default BigDecimal checkBalance(T account) throws AccountException{
        BigDecimal bdValanceValue;
        if (account.getAccountBalance() != null) {
            bdValanceValue = account.getAccountBalance().setScale(Account.SCALE_BALANCE, RoundingMode.UP);
        }else{
            bdValanceValue = new BigDecimal(0.00);
        }

        return bdValanceValue;
    }

    default String checkFormattedBalanceInLocalCurrency(T account){
        String bdValanceValue;
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        bdValanceValue = nf.format(Objects.requireNonNullElseGet(account.getAccountBalance(), () -> new BigDecimal(0.00)));

        return bdValanceValue;
    }

    default void printAccountStatement(U customer){
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("------------------------------------------ Resumo: Contas do cliente  ------------------------------------------\n");
        sb.append("------------------------------------------ Summary: Customer account  ------------------------------------------\n");
        sb.append("Customer name             : ").append(customer.getName()).append("\n");

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


}
