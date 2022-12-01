package br.com.letscode.trabalho.app;

import br.com.letscode.trabalho.app.console.ConsoleImpl;
import br.com.letscode.trabalho.entity.Customer;
import br.com.letscode.trabalho.exception.AccountException;
import br.com.letscode.trabalho.exception.CustomerException;
import br.com.letscode.trabalho.exception.MenuException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class MainApp {

    static ConsoleImpl console;

    public static void main(String[] args) {
        start();
    }

    private static void start(){
        HashMap<Integer, Menu> menus = new LinkedHashMap<>();
        menus.put(1, new Menu(1, "Open Account"));
        menus.put(2, new Menu(2, "Deposit"));
        menus.put(3, new Menu(3, "Withdrawal"));
        menus.put(4, new Menu(4, "Invest"));
        menus.put(5, new Menu(5, "Transfer"));
        menus.put(6, new Menu(6, "Check Statement"));
        menus.put(99, new Menu(99, "Exit"));

        execute(menus);

    }

    private static void execute(HashMap<Integer, Menu> menus){

        if (console == null) {
            console = new ConsoleImpl(menus, new Scanner(System.in));
        }

        try {
            StringBuffer sb = console.listMenu();
            System.out.println("===================================================================");
            System.out.println("Choose the option from menu:\n");
            System.out.println(sb);
            Integer menuChoice = console.quiz("Type the menu number: ");

            if (menuChoice != 99) {
                System.out.println("--> " + console.chooseOptionFromMenu(menuChoice));
                Customer customer = console.executeMenu(menuChoice);
                console.setCustomer(customer);
            }else {
                seeYouLater();
            }

            System.out.println("===================================================================\n");
        }catch(MenuException | AccountException | CustomerException ex){
            System.out.println(ex.getMessage());
        }

        execute(menus);
    }
    private static void seeYouLater(){
        System.out.println("Thanks for coming! See you :-)");
        System.exit(0);
    }
}
