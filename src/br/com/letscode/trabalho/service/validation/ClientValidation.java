package br.com.letscode.trabalho.service.validation;

import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.exception.CustomerException;

public interface ClientValidation<T extends Customer> {

    void validateDocument(T client) throws CustomerException;
    void formatDocument(T client) throws CustomerException;
}
