package br.com.letscode.trabalho.validation.customer;

import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.exception.CustomerException;

public interface CustomerValidations<T extends Customer> {
    void validate(T customer) throws CustomerException;
}
