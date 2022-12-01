package br.com.letscode.trabalho.dto;

import br.com.letscode.trabalho.entity.Account;
import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.entity.CustomerPF;
import br.com.letscode.trabalho.entity.CustomerPJ;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Objects;

public class PrinterDTO<T extends Account, U extends Customer> {

    public void printAccountStatement(Customer customer){
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("------------------------------------------ Resumo: Contas do cliente  ------------------------------------------\n");
        sb.append("------------------------------------------ Summary: Customer account  ------------------------------------------\n");
        sb.append("Customer name         : ").append(customer.getName().toUpperCase()).append("\n");

        if (customer instanceof CustomerPF) {
            sb.append("Document              : ").append(((CustomerPF) customer).getCpfFormatted()).append("\n");
        }else{
            sb.append("Document              : ").append(((CustomerPJ) customer).getCnpjFormatted()).append("\n");
        }

        for (Map.Entry<Integer, Account> accounts : customer.getAccounts().entrySet()) {
            Account varAccount = accounts.getValue();
            sb.append(" ---> Account ID      : ").append(varAccount.getId()).append("\n");
            sb.append(" ---> Account Type    : ").append(varAccount.getAccountLabel()).append("\n");
            sb.append(" ---> Account Balance : ").append(checkFormattedBalanceInLocalCurrency((T) varAccount, (U)customer)).append("\n");
            sb.append("\n");
        }
        sb.append("\n");

        System.out.println(sb);
    }

    public String checkFormattedBalanceInLocalCurrency(T account, U customer){
        String bdValanceValue = null;
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        bdValanceValue = nf.format(Objects.requireNonNullElseGet(account.getAccountBalance(), () -> new BigDecimal(0.00)));

        return bdValanceValue;
    }

    public String getFormattedBalanceInLocalCurrency(BigDecimal value){
        String bdValanceValue = null;
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        bdValanceValue = nf.format(Objects.requireNonNullElseGet(value, () -> new BigDecimal(0.00)));
        return bdValanceValue;
    }

}
