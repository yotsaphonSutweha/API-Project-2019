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
   
   public boolean updateCustomer(final Customer customer){
       for(int i = 0; i < customers.size(); i++){
           Customer c = customers.get(i);
           if(c.getId().equals(customer.getId())){
               customers.set(i, customer);
               return true;
           }
       }
       return false;
   }
   
   public Customer getCustomerById(String id) {
       for (Customer customer : customers) {
           if (customer.getId().equals(id)) {
               return customer;
           }
       }
       return new Customer();
   }
}
