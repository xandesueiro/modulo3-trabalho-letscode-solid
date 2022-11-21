package br.com.letscode.trabalho.service.validation;

import br.com.letscode.trabalho.entity.CustomerPF;
import br.com.letscode.trabalho.exception.CustomerException;

public class CustomerPFValidation implements CustomerValidation<CustomerPF>{
    @Override
    public void validateDocument(CustomerPF customer) throws CustomerException {
        if (customer.getDocument().length() != 11){
            throw new CustomerException("Invalid CPF document... it must contain 11 numbers - without separators");
        }
    }

    @Override
    public void formatDocument(CustomerPF customer) throws CustomerException {

        StringBuilder cpfBlocs = new StringBuilder();
        char[] charBlocs = customer.getDocument().toCharArray();

        for (int i = 0; i < charBlocs.length; i++) {
            if (i ==2) {
                cpfBlocs.append(charBlocs[i]).append(".");
            } else if (i == 5){
                cpfBlocs.append(charBlocs[i]).append(".");
            } else if (i == 8){
                cpfBlocs.append(charBlocs[i]).append("-");
            } else {
                cpfBlocs.append(charBlocs[i]);
            }
        }
        customer.setCpfFormatted(cpfBlocs.toString());
    }
}
