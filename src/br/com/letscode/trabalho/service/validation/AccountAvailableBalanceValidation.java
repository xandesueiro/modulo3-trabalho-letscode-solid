package br.com.letscode.trabalho.service.validation;

import br.com.letscode.trabalho.entity.Account;
import br.com.letscode.trabalho.exception.AccountException;

import java.math.BigDecimal;

public class AccountAvailableBalanceValidation implements AccountValidations{
    @Override
    public void validate(Account account, BigDecimal value) throws AccountException {
        if (value.doubleValue() <= account.getAccountBalance().doubleValue()) {
            throw new AccountException("Sorry, there is no available balance in your account "
                    + account.getAccountLabel()
                    + " : "
                    + account.getId()
                    + " for this transaction.");
        }
    }
}
