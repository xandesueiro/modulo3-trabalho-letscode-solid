package br.com.letscode.trabalho.service.account;

import br.com.letscode.trabalho.entity.Account;
import br.com.letscode.trabalho.entity.CustomerPJ;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;
import br.com.letscode.trabalho.utils.ConstantUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public interface AccountCyclePJ<T extends Account, U extends CustomerPJ>{

    T openAccount(String customerDocument, DocumentType documentType) throws AccountException, CustomerException;
    T openAccount(U customerPJ, BigDecimal balanceValue) throws AccountException, CustomerException;
    void deposit(T account, BigDecimal depositValue) throws AccountException;
    void invest(T account, BigDecimal investmentValue) throws AccountException;
    void withdrawal(T account, BigDecimal withdrawValue) throws AccountException;
    void transfer(T account, BigDecimal transferValue) throws AccountException;
    BigDecimal applyFee(T account, BigDecimal depositValue) throws AccountException;
    void applyIncome(T account, BigDecimal incomeValue) throws AccountException;

    default Integer generateAccountId(){
        Random random = new Random();
        return random.nextInt(ConstantUtils.ACCOUNT_RANGE_START, ConstantUtils.ACCOUNT_RANGE_END);
    }

    default void updateBalance(T account, BigDecimal newValue){
        if (newValue != null)
            account.setAccountBalance(newValue.setScale(ConstantUtils.ACCOUNT_SCALE_BALANCE, RoundingMode.UP));
        else
            account.setAccountBalance(new BigDecimal(0.00));
    }

}
