package br.com.letscode.trabalho.entity;

import java.util.HashMap;
import java.util.Objects;

public abstract class Customer {

    private Integer id;
    private String name;
    private String document;

    private HashMap<Integer, Account> accounts;

    public static final int TOTAL_AVAIABLE_IDS = 100;

    public Customer(String strName, String strDocument){
        this.name = strName;
        this.document = strDocument;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }

    public HashMap<Integer, Account> getAccounts() {
        return accounts;
    }

    public Account getAccount(Integer accountId){
        Account account = getAccounts().containsKey(accountId) ? getAccounts().get(accountId) : null;
        return account;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", document='").append(document).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) && Objects.equals(document, customer.document);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, document);
    }
}
