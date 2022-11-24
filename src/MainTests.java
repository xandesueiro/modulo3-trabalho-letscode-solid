
import br.com.letscode.trabalho.dto.PrinterDTO;
import br.com.letscode.trabalho.entity.Account;
import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.entity.CustomerPF;
import br.com.letscode.trabalho.entity.CustomerPJ;
import br.com.letscode.trabalho.enums.AccountType;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;
import br.com.letscode.trabalho.service.account.AccountServicePF;
import br.com.letscode.trabalho.service.account.AccountServicePJ;
import br.com.letscode.trabalho.validation.account.AccountAvailableBalanceValidation;
import br.com.letscode.trabalho.validation.account.AccountPositiveBalanceValidation;
import br.com.letscode.trabalho.validation.account.AccountValidations;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainTests {

    private static HashMap<String, AccountValidations> validationsList;

    public static void main(String[] args) {
        validationsList = new LinkedHashMap<>();
        validationsList.put("withdrawal0", new AccountPositiveBalanceValidation());
        validationsList.put("withdrawal1", new AccountAvailableBalanceValidation());
        validationsList.put("transfer0", new AccountPositiveBalanceValidation());
        validationsList.put("transfer1", new AccountAvailableBalanceValidation());

        workCustomerPF();
        workCustomerPJ();
    }

    private static void workCustomerPF(){
        AccountServicePF accountServicePF = new AccountServicePF(validationsList);
        PrinterDTO printerDTO = new PrinterDTO();
        CustomerPF customer;

        try {
            System.out.println("\n");
            System.out.println("============================== CUSTOMER PF ==============================\n");

            String strName = "alexandre sueiro";
            String strDocumento = "45269380765"; //example valid cpf

            customer = accountServicePF.openAccount(strName, strDocumento, DocumentType.CPF);
            System.out.println(customer);

            BigDecimal strCash = new BigDecimal(100.00);
            customer = accountServicePF.openAccount(customer, AccountType.CHECKING_ACCOUNT, strCash);
            System.out.println(customer);

            strCash = new BigDecimal(100.00);
            customer = accountServicePF.openAccount(customer, AccountType.SAVINGS_ACCOUNT, strCash);
            System.out.println(customer);

            strCash = new BigDecimal(100.00);
            customer = accountServicePF.openAccount(customer, AccountType.INVESTMENT_ACCOUNT, strCash);
            System.out.println(customer);

            printerDTO.printAccountStatement(customer);

            System.out.println("--> DEPOSIT: \n");
            depositTestsAllAccounts(customer, new BigDecimal(1000.00));

            System.out.println("\n--> INVEST: \n");
            investTestsAllAccounts(customer, new BigDecimal(500.00));

            System.out.println("\n--> WITHDRAWAL: \n");
            withdrawalTestsAllAccounts(customer, new BigDecimal(100.00));

            System.out.println("\n--> TRANSFER: \n");
            transferTestsAllAccounts(customer, new BigDecimal(100.00));

            printerDTO.printAccountStatement(customer);

            System.out.println("============ VALIDACAO DE ERROS PF");
            System.out.println("\n--> WITHDRAWAL: \n");
            withdrawalTestsAllAccounts(customer, new BigDecimal(100000000000.00));

            System.out.println("\n--> TRANSFER: \n");
            transferTestsAllAccounts(customer, new BigDecimal(100000000000.00));


        } catch (AccountException e) {
            System.out.println(e.getMessage());
        } catch (CustomerException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void workCustomerPJ(){
        AccountServicePJ accountServicePJ = new AccountServicePJ(validationsList);
        PrinterDTO printerDTO = new PrinterDTO();
        CustomerPJ customerPJ;

        try {
            System.out.println("\n");
            System.out.println("============================== CUSTOMER PJ ==============================\n");

            String strNamePJ = "code & cia";
            String strDocumentoPJ = "33760346000147"; //example valid cnpj

            customerPJ = accountServicePJ.openAccount(strNamePJ, strDocumentoPJ, DocumentType.CNPJ);
            System.out.println(customerPJ);

            BigDecimal strCashPJ = new BigDecimal(1000.00);
            customerPJ = accountServicePJ.openAccount(customerPJ, AccountType.CHECKING_ACCOUNT, strCashPJ);
            System.out.println(customerPJ);

            try {
                strCashPJ = new BigDecimal(1000.00);
                customerPJ = accountServicePJ.openAccount(customerPJ, AccountType.SAVINGS_ACCOUNT, strCashPJ);
                System.out.println(customerPJ);
            }catch (CustomerException customerException){
                System.out.println(customerException.getMessage());
            }

            strCashPJ = new BigDecimal(1000.00);
            customerPJ = accountServicePJ.openAccount(customerPJ, AccountType.INVESTMENT_ACCOUNT, strCashPJ);
            System.out.println(customerPJ + "\n");

            printerDTO.printAccountStatement(customerPJ);

            System.out.println("DEPOSIT: \n");
            depositTestsAllAccounts(customerPJ, new BigDecimal(5000.00));

            System.out.println("\n--> INVEST: \n");
            investTestsAllAccounts(customerPJ, new BigDecimal(1000.00));

            System.out.println("\n--> WITHDRAWAL: \n");
            withdrawalTestsAllAccounts(customerPJ, new BigDecimal(500.00));

            System.out.println("\n--> TRANSFER: \n");
            transferTestsAllAccounts(customerPJ, new BigDecimal(100.00));

            System.out.println("\n");
            printerDTO.printAccountStatement(customerPJ);

            System.out.println("============ VALIDACAO DE ERROS PJ");
            System.out.println("\n--> WITHDRAWAL: \n");
            withdrawalTestsAllAccounts(customerPJ, new BigDecimal(100000000000.00));

            System.out.println("\n--> TRANSFER: \n");
            transferTestsAllAccounts(customerPJ, new BigDecimal(100000000000.00));

        } catch (AccountException e) {
            System.out.println(e.getMessage());
        } catch (CustomerException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void depositTestsAllAccounts(Customer customer, BigDecimal value){

        PrinterDTO printerDTO = new PrinterDTO();

        System.out.println(customer);
        for (Account account: customer.getAccounts().values()) {
            try{
                BigDecimal beforeDeposit = account.getAccountBalance();

                if (customer instanceof CustomerPF) {
                    AccountServicePF accountServicePF = new AccountServicePF(validationsList);
                    accountServicePF.deposit(account, value);
                }else{
                    AccountServicePJ accountService = new AccountServicePJ(validationsList);
                    accountService.deposit(account, value);
                }
                BigDecimal afterDeposit = account.getAccountBalance();
                System.out.println(account.getAccountLabel()
                        + " - id: " + account.getId()
                        + " Before Balance: " + printerDTO.getFormattedBalanceInLocalCurrency(beforeDeposit)
                        + " /"
                        + " After Deposit: " + printerDTO.getFormattedBalanceInLocalCurrency(afterDeposit)
                );
            }catch (AccountException accountException){
                System.out.println(accountException.getMessage());
            }
        }

    }
    private static void investTestsAllAccounts(Customer customer, BigDecimal value){
        PrinterDTO printerDTO = new PrinterDTO();

        System.out.println(customer);
        for (Account account: customer.getAccounts().values()) {
            try{
                BigDecimal beforeInvest = account.getAccountBalance();

                if (customer instanceof CustomerPF) {
                    AccountServicePF accountServicePF = new AccountServicePF(validationsList);
                    accountServicePF.invest(account, value);
                }else{
                    AccountServicePJ accountService = new AccountServicePJ(validationsList);
                    accountService.invest(account, value);
                }

                BigDecimal afterInvest = account.getAccountBalance();
                System.out.println(account.getAccountLabel()
                        + " - id: " + account.getId()
                        + " Before Balance: " + printerDTO.getFormattedBalanceInLocalCurrency(beforeInvest)
                        + " /"
                        + " After Invest: " + printerDTO.getFormattedBalanceInLocalCurrency(afterInvest)
                );
            }catch (AccountException accountException){
                System.out.println(accountException.getMessage());
            }
        }
    }

    private static void withdrawalTestsAllAccounts(Customer customer, BigDecimal value){
        PrinterDTO printerDTO = new PrinterDTO();

        System.out.println(customer);
        for (Account account: customer.getAccounts().values()) {
            try{
                BigDecimal beforeWithdrawal = account.getAccountBalance();

                if (customer instanceof CustomerPF) {
                    AccountServicePF accountServicePF = new AccountServicePF(validationsList);
                    accountServicePF.withdrawal(account, value);
                }else{
                    AccountServicePJ accountService = new AccountServicePJ(validationsList);
                    accountService.withdrawal(account, value);
                }

                BigDecimal afterWithdrawal = account.getAccountBalance();
                System.out.println(account.getAccountLabel()
                        + " - id: " + account.getId()
                        + " Before Balance: " + printerDTO.getFormattedBalanceInLocalCurrency(beforeWithdrawal)
                        + " /"
                        + " After Withdrawal: " + printerDTO.getFormattedBalanceInLocalCurrency(afterWithdrawal)
                );
            }catch (AccountException accountException){
                System.out.println(accountException.getMessage());
            }
        }
    }

    private static void transferTestsAllAccounts(Customer customer, BigDecimal value){
        PrinterDTO printerDTO = new PrinterDTO();

        System.out.println(customer);
        for (Account account: customer.getAccounts().values()) {
            try{
                BigDecimal beforeTransfer = account.getAccountBalance();

                if (customer instanceof CustomerPF) {
                    AccountServicePF accountServicePF = new AccountServicePF(validationsList);
                    accountServicePF.transfer(account, value);
                }else{
                    AccountServicePJ accountService = new AccountServicePJ(validationsList);
                    accountService.transfer(account, value);
                }

                BigDecimal afterTransfer = account.getAccountBalance();
                System.out.println(account.getAccountLabel()
                        + " - id: " + account.getId()
                        + " Before Balance: " + printerDTO.getFormattedBalanceInLocalCurrency(beforeTransfer)
                        + " /"
                        + " After Transfer: " + printerDTO.getFormattedBalanceInLocalCurrency(afterTransfer)
                );
            }catch (AccountException accountException){
                System.out.println(accountException.getMessage());
            }
        }
    }
}