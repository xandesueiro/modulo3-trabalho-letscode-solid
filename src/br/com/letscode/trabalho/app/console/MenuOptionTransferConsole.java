package br.com.letscode.trabalho.app.console;

import br.com.letscode.trabalho.app.MenuOption;
import br.com.letscode.trabalho.dto.PrinterDTO;
import br.com.letscode.trabalho.entity.Account;
import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.entity.CustomerPF;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.service.account.AccountServicePF;
import br.com.letscode.trabalho.service.account.AccountServicePJ;
import br.com.letscode.trabalho.validation.account.AccountValidations;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;

public class MenuOptionTransferConsole implements MenuOption {

    Scanner scanner;

    public MenuOptionTransferConsole(Scanner paramScanner){
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
        }else {
            throw new AccountException("Id account doesn't exist");
        }

    }

}
