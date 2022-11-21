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
    T openAccount(U customer, BigDecimal balanceValue) throws AccountException, CustomerException;
    void deposit(U customer, BigDecimal depositValue) throws AccountException;
    void invest(U customer, BigDecimal investmentValue) throws AccountException;
    void withdraw(U customer, BigDecimal investmentValue) throws AccountException;
    void transfer(U customer, BigDecimal investmentValue) throws AccountException;

    default Integer generateAccountId(){
        Random random = new Random();
        return random.nextInt(Account.RANGE_START, Account.RANGE_END);
    }

    default void updateBalance(T account, BigDecimal newValue){
        if (newValue != null)
            account.setAccountBalance(newValue.setScale(Account.SCALE_BALANCE, RoundingMode.UP));
        else
            account.setAccountBalance(new BigDecimal(0.00));
    }

}
