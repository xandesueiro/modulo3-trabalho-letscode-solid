package br.com.letscode.trabalho.service;

import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.entity.CustomerPF;
import br.com.letscode.trabalho.entity.CustomerPJ;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.CustomerException;
import br.com.letscode.trabalho.service.validation.CustomerPFValidation;
import br.com.letscode.trabalho.service.validation.CustomerPJValidation;
import br.com.letscode.trabalho.service.validation.CustomerValidation;

import java.util.Random;

public class CustomerService {

    CustomerValidation customerValidation = null;

    public Customer saveCustomer(String strCustomerName, String strCustomerDocument, DocumentType documentType) throws CustomerException {

        Customer customer;

        if (documentType.equals(DocumentType.CPF)){
            customer = new CustomerPF(strCustomerName, strCustomerDocument);

            customerValidation = new CustomerPFValidation();
            customerValidation.validateDocument(customer);
            customerValidation.formatDocument(customer);
        } else if (documentType.equals(DocumentType.CNPJ)){
            customer = new CustomerPJ(strCustomerName, strCustomerDocument);

            customerValidation = new CustomerPJValidation();
            customerValidation.validateDocument(customer);
            customerValidation.formatDocument(customer);
        } else{
            throw new CustomerException("Error: Document type is invalid.");
        }

        customer.setId(generateCustomerId());
        return customer;
    }

    public Integer generateCustomerId(){
        Random random = new Random();
        return random.nextInt(Customer.TOTAL_AVAIABLE_IDS);
    }
}
