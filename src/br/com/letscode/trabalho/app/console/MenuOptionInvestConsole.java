package br.com.letscode.trabalho.app.console;

import br.com.letscode.trabalho.app.MenuOption;
import br.com.letscode.trabalho.dto.PrinterDTO;
import br.com.letscode.trabalho.entity.Account;
import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.entity.CustomerPF;
import br.com.letscode.trabalho.entity.InvestmentAccount;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.service.account.AccountServicePF;
import br.com.letscode.trabalho.service.account.AccountServicePJ;
import br.com.letscode.trabalho.validation.account.AccountValidations;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;

public class MenuOptionInvestConsole implements MenuOption {

    Scanner scanner;

    public MenuOptionInvestConsole(Scanner paramScanner){
        this.scanner = paramScanner;
    }
    @Override
    public String answer() {
        return scanner.nextLine();
    }

    @Override
    public void transaction(Customer customer,
                            Integer idAccount,
                            BigDecimal value,
                            HashMap<String, AccountValidations> validationsList) throws AccountException {

        PrinterDTO printerDTO = new PrinterDTO();
        if (customer.getAccounts().containsKey(idAccount)) {
            Account account = customer.getAccounts().get(idAccount);
            BigDecimal beforeInvest = account.getAccountBalance();

            if (customer instanceof CustomerPF) {
                AccountServicePF accountServicePF = new AccountServicePF(validationsList);
                accountServicePF.invest(account, value);
            }else{
                if (account instanceof InvestmentAccount) {
                    AccountServicePJ accountService = new AccountServicePJ(validationsList);
                    accountService.invest((InvestmentAccount) account, value);
                }
            }

            BigDecimal afterInvest = account.getAccountBalance();
            System.out.println(account.getAccountLabel()
                    + " - id: " + account.getId()
                    + " Before Balance: " + printerDTO.getFormattedBalanceInLocalCurrency(beforeInvest)
                    + " /"
                    + " After Invest: " + printerDTO.getFormattedBalanceInLocalCurrency(afterInvest)
            );
        }else {
            throw new AccountException("Id account doesn't exist");
        }

    }

}

