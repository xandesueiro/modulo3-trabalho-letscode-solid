package br.com.letscode.trabalho.app.console;


import br.com.letscode.trabalho.app.Menu;
import br.com.letscode.trabalho.app.Menus;
import br.com.letscode.trabalho.dto.PrinterDTO;
import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;
import br.com.letscode.trabalho.exception.MenuException;
import br.com.letscode.trabalho.utils.ConstantUtils;
import br.com.letscode.trabalho.validation.account.AccountAvailableBalanceValidation;
import br.com.letscode.trabalho.validation.account.AccountPositiveBalanceValidation;
import br.com.letscode.trabalho.validation.account.AccountValidations;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class ConsoleImpl implements Menus<HashMap<Integer, Menu>, Customer> {

    HashMap<Integer, Menu> menus;
    Scanner scanner;
    Customer customer;
    private static HashMap<String, AccountValidations> validationsList;

    public ConsoleImpl(HashMap<Integer, Menu> paramMenu, Scanner paramScanner){
        this.menus = paramMenu;
        this.scanner = paramScanner;

        validationsList = new LinkedHashMap<>();
        validationsList.put("withdrawal0", new AccountPositiveBalanceValidation());
        validationsList.put("withdrawal1", new AccountAvailableBalanceValidation());
        validationsList.put("transfer0", new AccountPositiveBalanceValidation());
        validationsList.put("transfer1", new AccountAvailableBalanceValidation());
    }

    @Override
    public StringBuffer listMenu() throws MenuException {
        if (menus == null){
            throw new MenuException("Menus is not loaded");
        }

        StringBuffer sb = new StringBuffer();
        if (customer != null) {
            for (Integer key : this.menus.keySet()) {
                Menu menu = this.menus.get(key);
                sb.append(menu.getIdMenu()).append(" - ").append(menu.getDescriptionMenu()).append("\n");
            }
        }else {
            Menu menu = this.menus.get(1);
            sb.append(menu.getIdMenu()).append(" - ").append(menu.getDescriptionMenu()).append("\n");
        }
        return sb;
    }

    @Override
    public String chooseOptionFromMenu(Integer option) throws MenuException {

        if (!this.menus.containsKey(option)) {
            throw new MenuException("Key does not exist");
        }
        Menu menu = this.menus.get(option);
        return menu.getDescriptionMenu();
    }

    @Override
    public Customer executeMenu(Integer option) throws MenuException, CustomerException, AccountException {

        switch (option){
            case ConstantUtils.MENU_OPEN_ACCOUNT : {
                openAccount();
                break;
            }
            case ConstantUtils.MENU_DEPOSIT_ACCOUNT: {
                deposit();
                break;
            }
            case ConstantUtils.MENU_WITHDRAWAL_ACCOUNT: {
                withdrawal();
                break;
            }
            case ConstantUtils.MENU_INVEST_ACCOUNT: {
                invest();
                break;
            }
            case ConstantUtils.MENU_TRANSFER_ACCOUNT: {
                transfer();
                break;
            }
            case ConstantUtils.MENU_CHECK_STATEMENT_ACCOUNT: {
                printCustomerStatement();
                break;
            }
        }
        return customer;
    }

    private void printCustomerStatement(){
        PrinterDTO printerDTO = new PrinterDTO();
        printerDTO.printAccountStatement(customer);
    }

    public Integer quiz(String question) throws AccountException {
        String answer;
        Integer choice = null;
        System.out.printf(question);
        answer = scanner.nextLine();
        if (answer != null ){
            try {
                choice = Integer.valueOf(answer);
            }catch (NumberFormatException numberFormatException){
                throw new AccountException("Invalid choice!");
            }
        }
        return choice;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    private void openAccount() throws CustomerException, AccountException {
        MenuOptionOpenAccountConsole menuOptionOpenAccountConsole = new MenuOptionOpenAccountConsole(scanner);
        if (customer == null) {
            customer = menuOptionOpenAccountConsole.menuOption_OpenAccount_FirstTime(validationsList);
        } else {
            menuOptionOpenAccountConsole.menuOption_OpenAccount(customer, validationsList);
        }
        printCustomerStatement();
    }

    private void deposit() throws CustomerException, AccountException {
        MenuOptionDepositConsole menuOptionDepositConsole = new MenuOptionDepositConsole(scanner);
        System.out.printf("Account ID       : ");
        String accountID = scanner.nextLine();
        Integer id = null;
        if (accountID != null ){
            try {
                id = Integer.valueOf(accountID);
                if(!customer.getAccounts().containsKey(id)){
                    throw new AccountException("Invalid account id!");
                }
            }catch (NumberFormatException numberFormatException){
                throw new AccountException("Invalid account id!");
            }
        }

        System.out.printf("Value            : R$ ");
        String value = scanner.nextLine();
        BigDecimal valueToInputAccount = null;
        if (value != null ){
            try {
                valueToInputAccount = new BigDecimal(value);
            }catch (NumberFormatException numberFormatException){
                throw new AccountException("Invalid value!");
            }
        }
        menuOptionDepositConsole.transaction(customer, id, valueToInputAccount, validationsList);
    }

    private void transfer() throws CustomerException, AccountException {
        MenuOptionTransferConsole menuOptionTransferConsole = new MenuOptionTransferConsole(scanner);
        System.out.printf("Account ID       : ");
        String accountID = scanner.nextLine();
        Integer id = null;
        if (accountID != null ){
            try {
                id = Integer.valueOf(accountID);
                if(!customer.getAccounts().containsKey(id)){
                    throw new AccountException("Invalid account id!");
                }
            }catch (NumberFormatException numberFormatException){
                throw new AccountException("Invalid account id!");
            }
        }

        System.out.printf("Value            : R$ ");
        String value = scanner.nextLine();
        BigDecimal valueToInputAccount = null;
        if (value != null ){
            try {
                valueToInputAccount = new BigDecimal(value);
            }catch (NumberFormatException numberFormatException){
                throw new AccountException("Invalid value!");
            }
        }
        menuOptionTransferConsole.transaction(customer, id, valueToInputAccount, validationsList);
    }

    private void withdrawal() throws CustomerException, AccountException {
        MenuOptionWithdrawalConsole menuOptionWithdrawalConsole = new MenuOptionWithdrawalConsole(scanner);
        System.out.printf("Account ID       : ");
        String accountID = scanner.nextLine();
        Integer id = null;
        if (accountID != null ){
            try {
                id = Integer.valueOf(accountID);
                if(!customer.getAccounts().containsKey(id)){
                    throw new AccountException("Invalid account id!");
                }
            }catch (NumberFormatException numberFormatException){
                throw new AccountException("Invalid account id!");
            }
        }

        System.out.printf("Value            : R$ ");
        String value = scanner.nextLine();
        BigDecimal valueToInputAccount = null;
        if (value != null ){
            try {
                valueToInputAccount = new BigDecimal(value);
            }catch (NumberFormatException numberFormatException){
                throw new AccountException("Invalid value!");
            }
        }
        menuOptionWithdrawalConsole.transaction(customer, id, valueToInputAccount, validationsList);
    }

    private void invest() throws CustomerException, AccountException {
        MenuOptionInvestConsole menuOptionInvestConsole = new MenuOptionInvestConsole(scanner);
        System.out.printf("Account ID       : ");
        String accountID = scanner.nextLine();
        Integer id = null;
        if (accountID != null ){
            try {
                id = Integer.valueOf(accountID);
                if(!customer.getAccounts().containsKey(id)){
                    throw new AccountException("Invalid account id!");
                }
            }catch (NumberFormatException numberFormatException){
                throw new AccountException("Invalid account id!");
            }
        }

        System.out.printf("Value            : R$ ");
        String value = scanner.nextLine();
        BigDecimal valueToInputAccount = null;
        if (value != null ){
            try {
                valueToInputAccount = new BigDecimal(value);
            }catch (NumberFormatException numberFormatException){
                throw new AccountException("Invalid value!");
            }
        }
        menuOptionInvestConsole.transaction(customer, id, valueToInputAccount, validationsList);
    }
}
