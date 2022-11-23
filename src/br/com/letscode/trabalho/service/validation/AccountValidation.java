package br.com.letscode.trabalho.service.validation;

import br.com.letscode.trabalho.entity.Account;

import java.math.BigDecimal;

public class AccountValidation implements BalanceValidation{

    @Override
    public boolean isBalancePositive(Account account) {
        BigDecimal baseBalanceAmount = new BigDecimal(0.00);
        if (account.getAccountBalance().doubleValue() > baseBalanceAmount.doubleValue()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean hasAvailableBalance(Account account, BigDecimal value) {
        if (value.doubleValue() <= account.getAccountBalance().doubleValue()){
            return true;
        }else {
            return false;
        }
    }
}
