package br.com.letscode.trabalho.app;

import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;
import br.com.letscode.trabalho.exception.MenuException;

import java.util.HashMap;

public interface Menus<T extends HashMap<Integer, Menu>, U extends Customer> {

    StringBuffer listMenu() throws MenuException;
    String chooseOptionFromMenu(Integer option) throws MenuException;

    U executeMenu(Integer option) throws MenuException, CustomerException, AccountException;

}
