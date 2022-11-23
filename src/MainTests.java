
import br.com.letscode.trabalho.dto.PrinterDTO;
import br.com.letscode.trabalho.entity.Account;
import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.entity.CustomerPF;
import br.com.letscode.trabalho.entity.CustomerPJ;
import br.com.letscode.trabalho.enums.AccountType;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;
import br.com.letscode.trabalho.service.AccountService;
import br.com.letscode.trabalho.service.AccountServicePJ;
import br.com.letscode.trabalho.service.validation.AccountAvailableBalanceValidation;
import br.com.letscode.trabalho.service.validation.AccountPositiveBalanceValidation;
import br.com.letscode.trabalho.service.validation.AccountValidations;

import java.math.BigDecimal;
import java.util.HashMap;

public class MainTests {

    private static HashMap<String, AccountValidations> validationsList;

    public static void main(String[] args) {
        validationsList = new HashMap<>();
        validationsList.put("withdraw0", new AccountPositiveBalanceValidation());
        validationsList.put("withdraw1", new AccountAvailableBalanceValidation());
        validationsList.put("transfer0", new AccountPositiveBalanceValidation());
        validationsList.put("transfer1", new AccountAvailableBalanceValidation());

        workCustomerPF();
        workCustomerPJ();
    }

    private static void workCustomerPF(){
        AccountService accountService = new AccountService(validationsList);
        PrinterDTO printerDTO = new PrinterDTO();
        CustomerPF customer;

        try {
            System.out.println("==============CUSTOMER PF==================\n");

            String strName = "alexandre sueiro";
            String strDocumento = "45269380765"; //example valid cpf

            customer = accountService.openAccount(strName, strDocumento, DocumentType.CPF);
            System.out.println(customer + "\n");

            BigDecimal strCash = new BigDecimal(100.00);
            customer = accountService.openAccount(customer, AccountType.CHECKING_ACCOUNT, strCash);
            System.out.println(customer + "\n");

            strCash = new BigDecimal(100.00);
            customer = accountService.openAccount(customer, AccountType.SAVINGS_ACCOUNT, strCash);
            System.out.println(customer + "\n");

            strCash = new BigDecimal(100.00);
            customer = accountService.openAccount(customer, AccountType.INVESTMENT_ACCOUNT, strCash);
            System.out.println(customer + "\n");

            printerDTO.printAccountStatement(customer);

            System.out.println("--> DEPOSIT: \n");
            depositTestsAllAccounts(customer, new BigDecimal(1000.00));

            System.out.println("\n--> INVEST: \n");
            investTestsAllAccounts(customer, new BigDecimal(500.00));

            System.out.println("\n--> WITHDRAW: \n");
            widhdrawTestsAllAccounts(customer, new BigDecimal(100.00));

            System.out.println("\n--> TRANSFER: \n");
            transferTestsAllAccounts(customer, new BigDecimal(100.00));

            System.out.println("\n");
            printerDTO.printAccountStatement(customer);

        } catch (AccountException e) {
            System.err.println(e.getMessage());
        } catch (CustomerException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void workCustomerPJ(){
        AccountServicePJ accountServicePJ = new AccountServicePJ(validationsList);
        PrinterDTO printerDTO = new PrinterDTO();
        CustomerPJ customerPJ;

        try {
            System.out.println("==============CUSTOMER PJ==================\n");

            String strNamePJ = "code & cia";
            String strDocumentoPJ = "33760346000147"; //example valid cnpj

            customerPJ = accountServicePJ.openAccount(strNamePJ, strDocumentoPJ, DocumentType.CNPJ);
            System.out.println(customerPJ + "\n");

            BigDecimal strCashPJ = new BigDecimal(1000.00);
            customerPJ = accountServicePJ.openAccount(customerPJ, AccountType.CHECKING_ACCOUNT, strCashPJ);
            System.out.println(customerPJ + "\n");

            try {
                strCashPJ = new BigDecimal(1000.00);
                customerPJ = accountServicePJ.openAccount(customerPJ, AccountType.SAVINGS_ACCOUNT, strCashPJ);
                System.out.println(customerPJ + "\n");
            }catch (CustomerException customerException){
                System.err.println(customerException.getMessage()+"\n");
            }

            strCashPJ = new BigDecimal(1000.00);
            customerPJ = accountServicePJ.openAccount(customerPJ, AccountType.INVESTMENT_ACCOUNT, strCashPJ);
            System.out.println(customerPJ + "\n");

            printerDTO.printAccountStatement(customerPJ);

            System.out.println("DEPOSIT: \n");
            depositTestsAllAccounts(customerPJ, new BigDecimal(5000.00));

            System.out.println("\n--> INVEST: \n");
            investTestsAllAccounts(customerPJ, new BigDecimal(1000.00));

            System.out.println("\n--> WITHDRAW: \n");
            widhdrawTestsAllAccounts(customerPJ, new BigDecimal(500.00));

            System.out.println("\n--> TRANSFER: \n");
            transferTestsAllAccounts(customerPJ, new BigDecimal(100.00));

            System.out.println("\n");
            printerDTO.printAccountStatement(customerPJ);


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
                    AccountService accountService = new AccountService(validationsList);
                    accountService.deposit(account, value);
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
                System.err.println(accountException.getMessage());
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
                    AccountService accountService = new AccountService(validationsList);
                    accountService.invest(account, value);
                }else{
                    AccountServicePJ accountService = new AccountServicePJ(validationsList);
                    accountService.invest(account, value);
                }

                BigDecimal afterInvest = account.getAccountBalance();
                System.err.println(account.getAccountLabel()
                        + " - id: " + account.getId()
                        + " Before Balance: " + printerDTO.getFormattedBalanceInLocalCurrency(beforeInvest)
                        + " /"
                        + " After Invest: " + printerDTO.getFormattedBalanceInLocalCurrency(afterInvest)
                );
            }catch (AccountException accountException){
                System.err.println(accountException.getMessage());
            }
        }
    }

    private static void widhdrawTestsAllAccounts(Customer customer, BigDecimal value){
        PrinterDTO printerDTO = new PrinterDTO();

        System.out.println(customer);
        for (Account account: customer.getAccounts().values()) {
            try{
                BigDecimal beforeWithdraw = account.getAccountBalance();

                if (customer instanceof CustomerPF) {
                    AccountService accountService = new AccountService(validationsList);
                    accountService.withdraw(account, value);
                }else{
                    AccountServicePJ accountService = new AccountServicePJ(validationsList);
                    accountService.withdraw(account, value);
                }

                BigDecimal afterWithdraw = account.getAccountBalance();
                System.out.println(account.getAccountLabel()
                        + " - id: " + account.getId()
                        + " Before Balance: " + printerDTO.getFormattedBalanceInLocalCurrency(beforeWithdraw)
                        + " /"
                        + " After Withdraw: " + printerDTO.getFormattedBalanceInLocalCurrency(afterWithdraw)
                );
            }catch (AccountException accountException){
                System.err.println(accountException.getMessage());
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
                    AccountService accountService = new AccountService(validationsList);
                    accountService.transfer(account, value);
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
                System.err.println(accountException.getMessage());
            }
        }
    }
}