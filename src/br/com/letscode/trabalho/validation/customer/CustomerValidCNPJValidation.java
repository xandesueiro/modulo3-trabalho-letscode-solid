package br.com.letscode.trabalho.validation.customer;

import br.com.letscode.trabalho.entity.CustomerPJ;
import br.com.letscode.trabalho.exception.CustomerException;

public class CustomerValidCNPJValidation implements CustomerValidations<CustomerPJ>{
    @Override
    public void validate(CustomerPJ customer) throws CustomerException {
        if (!isCNPJ(customer.getDocument())){
            throw new CustomerException("Invalid CNPJ document... watch out!");
        }
    }

    private static boolean isCNPJ(String CNPJ) {
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
