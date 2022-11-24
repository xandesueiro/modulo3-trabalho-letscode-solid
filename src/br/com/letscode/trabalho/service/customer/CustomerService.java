package br.com.letscode.trabalho.service.customer;

import br.com.letscode.trabalho.dto.FormatterDocumentDTO;
import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.entity.CustomerPF;
import br.com.letscode.trabalho.entity.CustomerPJ;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.CustomerException;
import br.com.letscode.trabalho.validation.customer.CustomerValidations;
import br.com.letscode.trabalho.utils.ConstantUtils;

import java.util.List;
import java.util.Random;

public class CustomerService {

    FormatterDocumentDTO formatterDocumentDTO;
    List<CustomerValidations> validationList;

    public CustomerService(FormatterDocumentDTO formatterDocumentDTO, List<CustomerValidations> lValidationList){
        this.formatterDocumentDTO = formatterDocumentDTO;
        this.validationList = lValidationList;
    }

    public Customer saveCustomer(String strCustomerName, String strCustomerDocument, DocumentType documentType) throws CustomerException {
        Customer customer;

        if (documentType.equals(DocumentType.CPF)){
            CustomerPF customerPF = new CustomerPF(strCustomerName, strCustomerDocument);
            String document = formatterDocumentDTO.formatDocument(customerPF);
            customerPF.setCpfFormatted(document);

            validate(customerPF);

            customer = customerPF;

        } else if (documentType.equals(DocumentType.CNPJ)){
            CustomerPJ customerPJ = new CustomerPJ(strCustomerName, strCustomerDocument);
            String document = formatterDocumentDTO.formatDocument(customerPJ);
            customerPJ.setCnpjFormatted(document);

            validate(customerPJ);

            customer = customerPJ;

        } else{
            throw new CustomerException("Error: Document type is invalid.");
        }

        customer.setId(generateCustomerId());
        return customer;
    }

    private Integer generateCustomerId(){
        Random random = new Random();
        return random.nextInt(ConstantUtils.CUSTOMER_TOTAL_AVAIABLE_IDS);
    }

    private void validate(Customer customer) throws CustomerException {
        for (CustomerValidations customerValidation: this.validationList) {
            customerValidation.validate(customer);
        }
    }
}
