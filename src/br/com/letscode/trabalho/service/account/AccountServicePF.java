package br.com.letscode.trabalho.service.account;

import br.com.letscode.trabalho.dto.FormatterDocumentDTO;
import br.com.letscode.trabalho.entity.*;
import br.com.letscode.trabalho.enums.AccountType;
import br.com.letscode.trabalho.enums.DocumentType;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;
import br.com.letscode.trabalho.service.customer.CustomerService;
import br.com.letscode.trabalho.validation.account.AccountValidations;
import br.com.letscode.trabalho.validation.customer.CustomerLengthCPFValidation;
import br.com.letscode.trabalho.validation.customer.CustomerValidCPFValidation;
import br.com.letscode.trabalho.validation.customer.CustomerValidations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountServicePF<T extends Account, U extends CustomerPF>{

    AccountCyclePF<CheckingAccount, CustomerPF> accountCycleCheckingAccountPF = new CheckingAccountServicePF();
    AccountCyclePF<SavingsAccount, CustomerPF> accountCycleSavingsAccountPF = new SavingsAccountServicePF();
    AccountCyclePF<InvestmentAccount, CustomerPF> accountCycleInvestmentAccountPF = new InvestmentAccountServicePF();

    HashMap<String, AccountValidations> validationList;

    public AccountServicePF(HashMap<String, AccountValidations> hmValidationList){
        this.validationList = hmValidationList;
    }

    public U openAccount(String customerName, String customerDocument, DocumentType documentType) throws AccountException, CustomerException{
        if (!isCustomerPF(documentType)){
            throw new CustomerException("It's not a good class to work... try AccountServicePJ");
        }
        AccountCycleOpen<CheckingAccount, CustomerPF>  accountCycleOpen = new CheckingAccountServicePF();
        CheckingAccount checkingAccount;
        Customer customer;

        checkingAccount = accountCycleOpen.openAccount();

        List<CustomerValidations> customerValidationList = new ArrayList<>();
        customerValidationList.add(new CustomerLengthCPFValidation());
        customerValidationList.add(new CustomerValidCPFValidation());
        CustomerService customerService = new CustomerService(new FormatterDocumentDTO(), customerValidationList);

        customer = customerService.saveCustomer(customerName, customerDocument, documentType);
        customer.addAccount(checkingAccount);

        return (U) customer;
    }

    public U openAccount(U customer, AccountType accountType, BigDecimal balanceValue) throws AccountException, CustomerException{
        Account account = null;
        switch (accountType){
            case CHECKING_ACCOUNT -> {
                AccountCycleOpen<CheckingAccount, CustomerPF>  accountCycleOpen = new CheckingAccountServicePF();
                account = accountCycleOpen.openAccount(customer, balanceValue);
                customer.addAccount(account);
            }
            case SAVINGS_ACCOUNT -> {
                AccountCycleOpen<SavingsAccount, CustomerPF> accountCycleOpen = new SavingsAccountServicePF();
                account = accountCycleOpen.openAccount(customer, balanceValue);
                customer.addAccount(account);
            }
            case INVESTMENT_ACCOUNT -> {
                AccountCycleOpen<InvestmentAccount, CustomerPF>  accountCycleOpen = new InvestmentAccountServicePF();
                account = accountCycleOpen.openAccount(customer, balanceValue);
                customer.addAccount(account);
            }
            default -> throw new AccountException("Error: account type not defined");
        }
        return customer;
    }

    public void deposit(Account account, BigDecimal depositValue) throws AccountException{
        if (account instanceof CheckingAccount){
            accountCycleCheckingAccountPF.deposit((CheckingAccount) account, depositValue);
        }else if (account instanceof SavingsAccount){
            accountCycleSavingsAccountPF.deposit((SavingsAccount) account, depositValue);
        }else if (account instanceof InvestmentAccount) {
            accountCycleInvestmentAccountPF.deposit((InvestmentAccount) account, depositValue);
        }else{
            throw new AccountException("Error: account type not defined");
        }
    }

    public void invest(Account account, BigDecimal investmentValue) throws AccountException{
        if (account instanceof SavingsAccount){
            //TODO: PRECISA IMPLEMENTAR A INTERFACE CORRETA (VER EXEMPLO EM CheckingAccountServicePF)
            //accountCycleSavingsAccountPF.invest((SavingsAccount) account, investmentValue);
        }else if (account instanceof InvestmentAccount) {
            //TODO: PRECISA IMPLEMENTAR A INTERFACE CORRETA (VER EXEMPLO EM CheckingAccountServicePF)
            //accountCycleInvestmentAccountPF.invest((InvestmentAccount) account, investmentValue);
        }else{
            throw new AccountException("Error: account type not defined");
        }
    }

    public void withdrawal(Account account, BigDecimal withdrawValue) throws AccountException{
        validateBalance(account, withdrawValue, "withdrawal");

        if (account instanceof CheckingAccount){
            accountCycleCheckingAccountPF.withdrawal((CheckingAccount) account, withdrawValue);
        }else if (account instanceof SavingsAccount){
            accountCycleSavingsAccountPF.withdrawal((SavingsAccount) account, withdrawValue);
        }else if (account instanceof InvestmentAccount) {
            accountCycleInvestmentAccountPF.withdrawal((InvestmentAccount) account, withdrawValue);
        }else{
            throw new AccountException("Error: account type not defined");
        }
    }

    public void transfer(Account account, BigDecimal transferValue) throws AccountException{
        validateBalance(account, transferValue, "transfer");

        if (account instanceof CheckingAccount){
            accountCycleCheckingAccountPF.transfer((CheckingAccount) account, transferValue);
        }else if (account instanceof SavingsAccount){
            accountCycleSavingsAccountPF.transfer((SavingsAccount) account, transferValue);
        }else if (account instanceof InvestmentAccount) {
            accountCycleInvestmentAccountPF.transfer((InvestmentAccount) account, transferValue);
        }else{
            throw new AccountException("Error: account type not defined");
        }
    }

    private boolean isCustomerPF(DocumentType documentType){
        return documentType.equals(DocumentType.CPF);
    }

    private void validateBalance(Account account, BigDecimal value, String keyTransaction) throws AccountException{
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
