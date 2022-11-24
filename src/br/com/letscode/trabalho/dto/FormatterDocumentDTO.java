package br.com.letscode.trabalho.dto;

import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.entity.CustomerPF;
import br.com.letscode.trabalho.entity.CustomerPJ;
import br.com.letscode.trabalho.exception.CustomerException;

public class FormatterDocumentDTO<T extends Customer> {

    public String formatDocument(Customer customer) throws CustomerException {
        String formattedDocument = null;
        if (customer instanceof CustomerPF) {
            StringBuilder cpfBlocs = formatCPF((CustomerPF) customer);
            formattedDocument = cpfBlocs.toString();

        }else if (customer instanceof CustomerPJ) {
            StringBuilder cnpjBlocs = formatCNPJ((CustomerPJ) customer);
            formattedDocument = cnpjBlocs.toString();
        }
        return formattedDocument;
    }

    private StringBuilder formatCPF(CustomerPF customer){
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
        return cpfBlocs;
    }

    private StringBuilder formatCNPJ(CustomerPJ customer){
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
        return cnpjBloc;
    }
}
