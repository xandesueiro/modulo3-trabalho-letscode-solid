package br.com.letscode.trabalho.service.validation;

import br.com.letscode.trabalho.entity.Account;
import br.com.letscode.trabalho.exception.AccountException;

import java.math.BigDecimal;

public class AccountPositiveBalanceValidation implements AccountValidations {
    @Override
    public void validate(Account account, BigDecimal notUsed) throws AccountException {
        BigDecimal baseBalanceAmount = new BigDecimal(0.00);
        if (account.getAccountBalance().doubleValue() < baseBalanceAmount.doubleValue()){
            throw new AccountException("Sorry, Your balance is negative and this transaction cannot be executed in your account "
                    + account.getAccountLabel()
                    + " : "
                    + account.getId()
                    + ".");
        }
    }
}
