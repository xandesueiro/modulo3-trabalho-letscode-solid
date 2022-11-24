package br.com.letscode.trabalho.validation.customer;

import br.com.letscode.trabalho.entity.CustomerPJ;
import br.com.letscode.trabalho.exception.CustomerException;

public class CustomerLengthCNPJValidation implements CustomerValidations<CustomerPJ> {
    @Override
    public void validate(CustomerPJ customer) throws CustomerException {
        if (customer.getDocument().length() != 14){
            throw new CustomerException("Invalid CNPJ document... it must contain 14 numbers - without separators");
        }
    }
}
