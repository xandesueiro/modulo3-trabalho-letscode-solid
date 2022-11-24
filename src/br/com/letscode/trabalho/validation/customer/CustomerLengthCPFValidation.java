package br.com.letscode.trabalho.validation.customer;

import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.exception.CustomerException;

public class CustomerLengthCPFValidation implements CustomerValidations<Customer>{
    @Override
    public void validate(Customer customer) throws CustomerException {
        if (customer.getDocument().length() != 11){
            throw new CustomerException("Invalid CPF document... it must contain 11 numbers - without separators");
        }
    }
}
