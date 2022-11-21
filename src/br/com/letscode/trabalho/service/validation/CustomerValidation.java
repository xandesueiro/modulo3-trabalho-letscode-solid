package br.com.letscode.trabalho.service.validation;

import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.exception.CustomerException;

public interface CustomerValidation<T extends Customer> {

    void validateDocument(T customer) throws CustomerException;
    void formatDocument(T customer) throws CustomerException;
}
