package br.com.letscode.trabalho.app.console;

import br.com.letscode.trabalho.app.MenuOption;
import br.com.letscode.trabalho.app.MenuOptionOpenAccount;
import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.entity.CustomerPF;
import br.com.letscode.trabalho.entity.CustomerPJ;
import br.com.letscode.trabalho.enums.AccountType;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;
import br.com.letscode.trabalho.service.account.AccountServicePF;
import br.com.letscode.trabalho.service.account.AccountServicePJ;
import br.com.letscode.trabalho.utils.ConstantUtils;
import br.com.letscode.trabalho.validation.account.AccountValidations;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;

class MenuOptionOpenAccountConsole implements MenuOptionOpenAccount {

    Scanner scanner;

    public MenuOptionOpenAccountConsole(Scanner paramScanner){
        this.scanner = paramScanner;
    }

    public Customer menuOption_OpenAccount_FirstTime(HashMap<String, AccountValidations> validationsList)
            throws CustomerException, AccountException {
        System.out.printf("Type your name              : ");
        String name = answer();
        System.out.printf("Are you using CPF or CNPJ?  : ");
        String documentType = answer();
        System.out.printf("Type your document number   : ");
        String documentNumber = answer();

        Customer customer = null;
        if (documentType.equalsIgnoreCase(ConstantUtils.DOCUMENT_CPF)) {
            AccountServicePF accountServicePF = new AccountServicePF(validationsList);
            customer = accountServicePF.openAccount(name, documentNumber, DocumentType.CPF);

        }else if (documentType.equalsIgnoreCase(ConstantUtils.DOCUMENT_CNPJ)) {
            AccountServicePJ accountServicePJ = new AccountServicePJ(validationsList);
            customer = accountServicePJ.openAccount(name, documentNumber, DocumentType.CNPJ);

        }else {
            System.err.println("!!!! BAD CHOICE !!!!");
            System.exit(0);
        }
        return customer;
    }

    public void menuOption_OpenAccount(Customer customer,
                                       HashMap<String, AccountValidations> validationsList)
            throws CustomerException, AccountException {
        System.out.println("What type of account would you like to open? (type the number of option)");

        if (customer instanceof CustomerPF ){
            System.out.printf("1- Checking Account | 2- Savings Account | 3- Investment Account     : ");
        }else{
            System.out.printf("1- Checking Account | 3- Investment Account     : ");
        }

        String type = answer();
        Integer accountType = null;
        if (type != null ){
            try {
                accountType = Integer.valueOf(type);
            }catch (NumberFormatException numberFormatException){
                throw new AccountException("Invalid account type!");
            }
        }

        System.out.printf("What amount do you want to deposit into the account? (e.g. 1000.50)  : R$ ");
        String value = answer();
        BigDecimal strCash = null;
        if (value != null ){
            try {
                strCash = new BigDecimal(value);
            }catch (NumberFormatException numberFormatException){
                strCash = new BigDecimal(0.00);
            }
        }

        if (accountType == ConstantUtils.MENU_CHECKING_ACCOUNT) {
            if (customer instanceof CustomerPF) {
                AccountServicePF accountServicePF = new AccountServicePF(validationsList);
                accountServicePF.openAccount((CustomerPF) customer, AccountType.CHECKING_ACCOUNT, strCash);
            }else{
                AccountServicePJ accountServicePJ = new AccountServicePJ(validationsList);
                accountServicePJ.openAccount((CustomerPJ) customer, AccountType.CHECKING_ACCOUNT, strCash);
            }

        }else if (accountType == ConstantUtils.MENU_SAVINGS_ACCOUNT) {
            if (customer instanceof CustomerPF) {
                AccountServicePF accountServicePF = new AccountServicePF(validationsList);
                accountServicePF.openAccount((CustomerPF) customer, AccountType.SAVINGS_ACCOUNT, strCash);
            }

        }else if (accountType == ConstantUtils.MENU_INVESTMENT_ACCOUNT) {
            if (customer instanceof CustomerPF) {
                AccountServicePF accountServicePF = new AccountServicePF(validationsList);
                accountServicePF.openAccount((CustomerPF) customer, AccountType.INVESTMENT_ACCOUNT, strCash);
            }else{
                AccountServicePJ accountServicePJ = new AccountServicePJ(validationsList);
                accountServicePJ.openAccount((CustomerPJ) customer, AccountType.INVESTMENT_ACCOUNT, strCash);
            }

        }else {
            System.err.println("!!!! BAD ACCOUNT TYPE CHOICE !!!!");
            System.exit(0);
        }
    }

    @Override
    public String answer() {
        return scanner.nextLine();
    }
}
