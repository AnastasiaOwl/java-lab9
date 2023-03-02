package main.view;


import main.entities.Customer;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ViewCustomer {
    public void showList(List<Customer> customers) {
        for (int i = 0; i < customers.size(); i++) {
            System.out.println(customers.get(i));
        }
        System.out.println("--------------------");
    }
    public void showOnlyYear(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.println("--------------------");
    }

    public void showSortByCardBalanceForEveryYear(Map<Integer, Customer> map) {
        for (Map.Entry entry : map.entrySet()) {
            System.out.println(entry.getKey()+" - "+ entry.getValue());
        }
        System.out.println("--------------------");
    }

    public void showDebtors(List<Customer> customers, int count) {
        System.out.println("number of debtors=" + count);

        for (int i = 0; i < customers.size(); i++) {
            System.out.println(customers.get(i));
        }
        System.out.println("--------------------");
    }
}
