package br.com.letscode.trabalho.entity;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class Account {

    private Integer id;
    protected BigDecimal accountBalance;
    protected String accountLabel;

    public static final int RANGE_START = 500;
    public static final int RANGE_END = 800;
    public static final int SCALE_BALANCE = 2;

    public Integer getId() {
        return id;
    }


    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public String getAccountLabel() {
        return accountLabel;
    }

    public void setAccountLabel(String accountLabel) {
        this.accountLabel = accountLabel;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("id=").append(id);
        sb.append(", accountBalance=").append(accountBalance);
        sb.append(", accountLabel='").append(accountLabel).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
