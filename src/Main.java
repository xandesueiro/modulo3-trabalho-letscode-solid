
import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.enums.AccountType;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;
import br.com.letscode.trabalho.service.AccountService;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        String strName = "alexandre sueiro";
        String strDocumento = "11122233344";

        AccountService accountService = new AccountService();
        Customer customer = null;

        try {
            System.out.println("==============CLIENTE PF==================\n");

            customer = accountService.openAccount(strName, strDocumento, DocumentType.CPF);
            System.out.println(customer.toString() + "\n");

            BigDecimal strCash = new BigDecimal(150.50);
            customer = accountService.openAccount(customer, AccountType.CHECKING_ACCOUNT, strCash);
            System.out.println(customer.toString() + "\n");

            strCash = strCash.multiply(new BigDecimal(5));
            customer = accountService.openAccount(customer, AccountType.SAVINGS_ACCOUNT, strCash);
            System.out.println(customer.toString() + "\n");

            strCash = strCash.multiply(new BigDecimal(5));
            customer = accountService.openAccount(customer, AccountType.INVESTIMENT_ACCOUNT, strCash);
            System.out.println(customer.toString() + "\n");

            accountService.printAccountStatement(customer);

            System.out.println("==============CLIENTE PJ==================\n");

        } catch (AccountException e) {
            System.out.println(e.getMessage());
        } catch (CustomerException e) {
            System.out.println(e.getMessage());
        }

    }

}