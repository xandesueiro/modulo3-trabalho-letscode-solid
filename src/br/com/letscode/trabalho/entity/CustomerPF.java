package br.com.letscode.trabalho.entity;

public class CustomerPF extends Customer {

    private String cpfFormatted;

    public CustomerPF(String strName, String strDocument) {
        super(strName, strDocument);
    }

    public String getCpfFormatted() {
        return cpfFormatted;
    }

    public void setCpfFormatted(String cpfFormatted) {
        this.cpfFormatted = cpfFormatted;
    }

}
