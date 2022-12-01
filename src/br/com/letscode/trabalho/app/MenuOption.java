package br.com.letscode.trabalho.app;

import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.validation.account.AccountValidations;

import java.math.BigDecimal;
import java.util.HashMap;

public interface MenuOption {
    String answer();
    void transaction(Customer customer,
                        Integer idAccount,
                        BigDecimal value,
                        HashMap<String, AccountValidations> validationsList) throws AccountException;
}
