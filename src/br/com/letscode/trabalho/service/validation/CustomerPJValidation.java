package br.com.letscode.trabalho.service.validation;

import br.com.letscode.trabalho.entity.CustomerPJ;
import br.com.letscode.trabalho.exception.CustomerException;

public class CustomerPJValidation implements CustomerValidation<CustomerPJ>{
    @Override
    public void validateDocument(CustomerPJ customer) throws CustomerException {
        if (customer.getDocument().length() != 14){
            throw new CustomerException("Invalid CNPJ document... it must contain 14 numbers - without separators");
        }

        if (!isCNPJ(customer.getDocument())){
            throw new CustomerException("Invalid CNPJ document... watch out!");
        }
    }

    @Override
    public void formatDocument(CustomerPJ customer) throws CustomerException {
        if (customer.getDocument() == null){
            throw new CustomerException("Error: filed document is required. Watch out!");
        }

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

    public static boolean isCNPJ(String CNPJ) {
        char dig13, dig14;
        int sm, i, r, num, peso;

        /*--------1st digit------------------*/
        sm = 0;
        peso = 2;
        for (i=11; i>=0; i--) {
            num = (CNPJ.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso + 1;
            if (peso == 10)
                peso = 2;
        }

        r = sm % 11;
        if ((r == 0) || (r == 1))
            dig13 = '0';
        else dig13 = (char)((11-r) + 48);

        /*--------2nd digit------------------*/
        sm = 0;
        peso = 2;
        for (i=12; i>=0; i--) {
            num = (CNPJ.charAt(i)- 48);
            sm = sm + (num * peso);
            peso = peso + 1;
            if (peso == 10)
                peso = 2;
        }

        r = sm % 11;
        if ((r == 0) || (r == 1))
            dig14 = '0';
        else dig14 = (char)((11-r) + 48);

        /*--------Same digits?------------------*/
        return (dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13));
    }
}
