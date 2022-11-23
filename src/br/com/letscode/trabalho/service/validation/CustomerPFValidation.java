package br.com.letscode.trabalho.service.validation;

import br.com.letscode.trabalho.entity.CustomerPF;
import br.com.letscode.trabalho.exception.CustomerException;

public class CustomerPFValidation implements CustomerValidation<CustomerPF>{
    @Override
    public void validateDocument(CustomerPF customer) throws CustomerException {
        if (customer.getDocument().length() != 11){
            throw new CustomerException("Invalid CPF document... it must contain 11 numbers - without separators");
        }

        if (!isCPF(customer.getDocument())){
            throw new CustomerException("Invalid CPF document... watch out!");
        }
    }

    @Override
    public void formatDocument(CustomerPF customer) throws CustomerException {
        if (customer.getDocument() == null){
            throw new CustomerException("Error: filed document is required. Watch out!");
        }

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

    private boolean isCPF(String CPF){
        char dig10, dig11;
        int sm, i, r, num, peso;

        /*--------1st digit------------------*/
        sm = 0;
        peso = 10;
        for (i=0; i<9; i++) {
            num = CPF.charAt(i) - 48;
            sm = sm + (num * peso);
            peso = peso - 1;
        }

        r = 11 - (sm % 11);
        if ((r == 10) || (r == 11))
            dig10 = '0';
        else dig10 = (char)(r + 48);

        /*--------2nd digit------------------*/
        sm = 0;
        peso = 11;
        for(i=0; i<10; i++) {
            num = (CPF.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
        }

        r = 11 - (sm % 11);
        if ((r == 10) || (r == 11))
            dig11 = '0';
        else dig11 = (char)(r + 48);

        /*--------Same digits?------------------*/
        return (dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10));
    }
}
