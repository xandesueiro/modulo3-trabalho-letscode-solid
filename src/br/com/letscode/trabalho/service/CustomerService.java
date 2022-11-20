package br.com.letscode.trabalho.service;

import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.CustomerException;

public class CustomerService implements CustomerCycle<Customer>{
    @Override
    public Customer saveCustomer(String strCustomerName, String strCustomerDocument, DocumentType documentType) throws CustomerException {
        return null;
    }
}
