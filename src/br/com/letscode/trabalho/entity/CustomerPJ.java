package br.com.letscode.trabalho.entity;

public class CustomerPJ extends Customer {

    private String cnpjFormatted;

    public CustomerPJ(String strName, String strDocument) {
        super(strName, strDocument);
    }

    public String getCnpjFormatted() {
        return this.cnpjFormatted;
    }

    public void setCpfFormatted(String cnpjFormatted) {
        this.cnpjFormatted = cnpjFormatted;
    }

}
