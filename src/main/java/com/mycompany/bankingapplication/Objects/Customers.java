/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankingapplication.Objects;

import java.util.ArrayList;

/**
 *
 * @author yo
 */
public class Customers {
   private ArrayList<Customer> customers = new ArrayList<>();
   private static Customers allCustomers = new Customers();
   
   public static Customers getInstance() {
       return allCustomers;
   }
   
   public ArrayList<Customer> getCustomerList(){
       return customers;
   }
   
   public String addCustomer(Customer customer) {
       String newId = Integer.toString(customers.size() + 1);
       customer.setId(newId);
       customers.add(customer);
       return newId;
   }
   
   public Customer getCustomerById(String id) {
       for (Customer customer : customers) {
           if (customer.getId().equals(id)) {
               return customer;
           }
       }
       return null;
   }
}
