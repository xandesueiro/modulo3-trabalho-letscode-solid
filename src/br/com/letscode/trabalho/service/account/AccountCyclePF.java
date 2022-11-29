package br.com.letscode.trabalho.service.account;

import br.com.letscode.trabalho.entity.Account;
import br.com.letscode.trabalho.entity.CustomerPF;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;
import br.com.letscode.trabalho.utils.ConstantUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public interface AccountCyclePF<T extends Account, U extends CustomerPF>
        extends AccountCycle<T, U> {

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
