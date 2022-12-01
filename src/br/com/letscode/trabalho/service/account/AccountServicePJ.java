package br.com.letscode.trabalho.service.account;

import br.com.letscode.trabalho.dto.FormatterDocumentDTO;
import br.com.letscode.trabalho.entity.*;
import br.com.letscode.trabalho.enums.AccountType;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;
import br.com.letscode.trabalho.service.customer.CustomerService;
import br.com.letscode.trabalho.validation.account.AccountValidations;
import br.com.letscode.trabalho.validation.customer.CustomerLengthCNPJValidation;
import br.com.letscode.trabalho.validation.customer.CustomerValidCNPJValidation;
import br.com.letscode.trabalho.validation.customer.CustomerValidations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountServicePJ  <T extends Account, U extends CustomerPJ>{
    AccountCyclePJ<CheckingAccount, CustomerPJ> accountCycleCheckingAccountPJ = new CheckingAccountServicePJ();
    AccountCyclePJ<InvestmentAccount, CustomerPJ> accountCycleInvestmentAccountPJ = new InvestmentAccountServicePJ();

    HashMap<String, AccountValidations> validationList;

    public AccountServicePJ(HashMap<String, AccountValidations> hmValidationList){
        this.validationList = hmValidationList;
    }

    public U openAccount(String customerName, String customerDocument, DocumentType documentType) throws AccountException, CustomerException {
        if (!isCustomerPJ(documentType)){
            throw new CustomerException("It's not a good class to work... try AccountService for Customer PF");
        }
        CheckingAccount checkingAccount;
        Customer customer;

        AccountCycleOpen<CheckingAccount, CustomerPJ>  accountCycleOpen = new CheckingAccountServicePJ();
        checkingAccount =  accountCycleOpen.openAccount(customerDocument, documentType);

        List<CustomerValidations> customerValidationList = new ArrayList<>();
        customerValidationList.add(new CustomerLengthCNPJValidation());
        customerValidationList.add(new CustomerValidCNPJValidation());
        CustomerService customerService = new CustomerService(new FormatterDocumentDTO(), customerValidationList);

        customer = customerService.saveCustomer(customerName, customerDocument, documentType);
        customer.addAccount(checkingAccount);

        return (U) customer;
    }

    public U openAccount(U customer, AccountType accountType, BigDecimal balanceValue) throws AccountException, CustomerException{
        Account account;
        switch (accountType){
            case CHECKING_ACCOUNT -> {
                AccountCycleOpen<CheckingAccount, CustomerPJ>  accountCycleOpen = new CheckingAccountServicePJ();
                account = accountCycleOpen.openAccount(customer, balanceValue);
                customer.addAccount(account);
            }
            case INVESTMENT_ACCOUNT -> {
                AccountCycleOpen<InvestmentAccount, CustomerPJ> accountCycleOpen = new InvestmentAccountServicePJ();
                account = accountCycleOpen.openAccount(customer, balanceValue);
                customer.addAccount(account);
            }
            default -> throw new AccountException("Error: account type not defined");
        }
        return (U) customer;
    }

    public void deposit(T account, BigDecimal depositValue) throws AccountException{
        if (account instanceof CheckingAccount){
            accountCycleCheckingAccountPJ.deposit((CheckingAccount) account, depositValue);
        }else if (account instanceof InvestmentAccount) {
            accountCycleInvestmentAccountPJ.deposit((InvestmentAccount) account, depositValue);
        }else{
            throw new AccountException("Error: account type not defined");
        }
    }

    public void invest(InvestmentAccount account, BigDecimal investmentValue) throws AccountException{
        AccountCycleIncome accountCycleIncome = new InvestmentAccountServicePJ();
        accountCycleIncome.invest(account, investmentValue);
    }

    public void withdrawal(T account, BigDecimal withdrawValue) throws AccountException{
        validateBalance(account, withdrawValue, "withdrawal");

        if (account instanceof CheckingAccount){
            accountCycleCheckingAccountPJ.withdrawal((CheckingAccount) account, withdrawValue);
        }else if (account instanceof InvestmentAccount) {
            accountCycleInvestmentAccountPJ.withdrawal((InvestmentAccount) account, withdrawValue);
        }else{
            throw new AccountException("Error: account type not defined");
        }
    }

    public void transfer(T account, BigDecimal transferValue) throws AccountException{
        validateBalance(account, transferValue, "transfer");

        if (account instanceof CheckingAccount){
            accountCycleCheckingAccountPJ.transfer((CheckingAccount) account, transferValue);
        }else if (account instanceof InvestmentAccount) {
            accountCycleInvestmentAccountPJ.transfer((InvestmentAccount) account, transferValue);
        }else{
            throw new AccountException("Error: account type not defined");
        }
    }

    private boolean isCustomerPJ(DocumentType documentType){
        return documentType.equals(DocumentType.CNPJ);
    }

    public void validateBalance(Account account, BigDecimal value, String keyTransaction) throws AccountException{
        int i = 0;
        for (String key : this.validationList.keySet()  ) {
            if (key.equalsIgnoreCase(keyTransaction.concat( Integer.toString(i) ))){
                AccountValidations accountValidations = this.validationList.get(key);
                accountValidations.validate(account, value);
                i++;
            }
        }
    }
}
