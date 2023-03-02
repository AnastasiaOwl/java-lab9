package main;

import lombok.SneakyThrows;
import main.server.CustomerServer;
import main.entities.Customer;
import main.view.ViewCustomer;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;


public class Main {
    private Scanner scanner;
    private CustomerServer CustomerServer;
    private ViewCustomer ViewCustomer;
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    @SneakyThrows
    private void run() {
         String nameSearch=null;
        Properties props = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(Path.of("config.properties"))) {
            props.load(reader);
            Connection connection = DriverManager.getConnection(props.getProperty("url"), props);
            CustomerServer = new CustomerServer(connection);
            ViewCustomer = new ViewCustomer();
        }
        scanner = new Scanner(System.in);
        int m;
        while ((m = menu()) != 0) {
            switch (m) {
                case 1 -> {
                    showAll();
                }
                case 2 -> {
                    searchByName(nameSearch);
                }
                case 3 -> {
                    searchByCardNumber();
                }
                case 4 -> {
                    sortByCardBalance();
                }
                case 5 -> {
                    sortByCardBalanceAndCardNumber();
                }
                case 6 ->{
                    getOnlyYear();
                }
                case 7 -> {
                    sortByCardBalanceForEveryYear ();
                }
            }
        }
    }
    private int menu() {
        System.out.println("""
                1. Show All
                2. Search by Name
                3. Search by cardNumber
                4. Sort by cardBalance
                5. Sort by CardBalance and CardNumber
                6. Get only year
                7. Sort by CardBalance for every year
                0. Exit
                """
        );
        return Integer.parseInt(scanner.nextLine());
    }

    void showAll() {
        List<Customer> customers = CustomerServer.findAll();
        System.out.println("--- All Customers ---");
        ViewCustomer.showList(customers);
    }

    void searchByName(String nameSearch) {
        List<Customer> customers = CustomerServer.searchByName(nameSearch,scanner);
        System.out.println("---  Search by name:  ---");
        ViewCustomer.showList(customers);
    }
    void searchByCardNumber(){
        List<Customer> customers = CustomerServer.searchByCardNumber(scanner);
        System.out.println("---  Search by CardNumber:  ---");
        ViewCustomer.showList(customers);
    }
    void sortByCardBalance(){
        int count=CustomerServer.numberOfDebtors();
        List<Customer> customers = CustomerServer.sortByCardBalance();
        System.out.println("---  Sort by CardBalance:  ---");
        ViewCustomer.showDebtors(customers,count);
    }
   void sortByCardBalanceAndCardNumber(){
       List<Customer> customers = CustomerServer.sortByCardBalanceAndCardNumber();
       System.out.println("---  Sort by CardBalance and CardNumber:  ---");
       ViewCustomer.showList(customers);
   }
   void getOnlyYear(){
       List<Integer> list = CustomerServer.getOnlyYear();
       System.out.println("---  Get only year:  ---");
       ViewCustomer.showOnlyYear(list);
   }
   void sortByCardBalanceForEveryYear (){
       Map<Integer, Customer> map = CustomerServer.sortByCardBalanceForEveryYear();
       System.out.println("---  Sort by CardBalance for every year:  ---");
       ViewCustomer.showSortByCardBalanceForEveryYear (map);
   }
}