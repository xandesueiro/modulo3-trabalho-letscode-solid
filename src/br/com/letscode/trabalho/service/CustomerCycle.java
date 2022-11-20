package br.com.letscode.trabalho.service;

import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.CustomerException;

import java.util.Random;

public interface CustomerCycle <T extends Customer>{

    Customer saveCustomer(String strCustomerName, String strCustomerDocument, DocumentType documentType) throws CustomerException;

    default Integer generateCustomerId(){
        Random random = new Random();
        return random.nextInt(Customer.TOTAL_AVAIABLE_IDS);
    }
}
