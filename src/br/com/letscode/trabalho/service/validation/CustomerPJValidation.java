package br.com.letscode.trabalho.service.validation;

import br.com.letscode.trabalho.entity.CustomerPJ;
import br.com.letscode.trabalho.exception.CustomerException;

public class CustomerPJValidation implements CustomerValidation<CustomerPJ>{
    @Override
    public void validateDocument(CustomerPJ customer) throws CustomerException {
        if (customer.getDocument().length() != 14){
            throw new CustomerException("Invalid CNPJ document... it must contain 14 numbers - without separators");
        }
    }

    @Override
    public void formatDocument(CustomerPJ customer) throws CustomerException {
        StringBuilder cnpjBloc = new StringBuilder();
        char[] charBlocs = customer.getDocument().toCharArray();

        for (int i = 0; i < charBlocs.length; i++) {
            //11.111.111/0001-11
            if (i ==1) {
                cnpjBloc.append(charBlocs[i]).append(".");
            } else if (i == 4){
                cnpjBloc.append(charBlocs[i]).append(".");
            } else if (i == 7){
                cnpjBloc.append(charBlocs[i]).append("/");
            } else if (i == 11){
                cnpjBloc.append(charBlocs[i]).append("-");
            } else {
                cnpjBloc.append(charBlocs[i]);
            }
        }
        customer.setCnpjFormatted(cnpjBloc.toString());
    }
}
