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

public class CustomersDataService {
   private ArrayList<Customer> customers = new ArrayList<>();
   private static CustomersDataService allCustomers = new CustomersDataService();
   
   
   public static CustomersDataService getInstance() {
       return allCustomers;
   }
   
   public ArrayList<Customer> getCustomers(){
       return customers;
   }
   
   
   public String addCustomer(Customer customer) {
       String newId = "";
       String existingId = "";
       if (customers.isEmpty()) {
           newId = Integer.toString(customers.size() + 1);
           customer.setId(newId);
           customers.add(customer);
       } else {
           Customer lastCustomer = customers.get(customers.size() - 1);
           existingId = lastCustomer.getId();
           newId = Integer.toString(Integer.parseInt(existingId) + 1);
           customer.setId(newId);
           customers.add(customer);
       }
       return newId;
   }
   
   public void deleteCustomerbyName(String firstName) {
       for (Customer customer : customers) {
           if(customer.getFirstName().equals(firstName)) {
               customers.remove(customer);
           }
       }
   }
   
   public void deleteCustomerById(String id) {
        for (Customer customer : customers) {
           if(customer.getFirstName().equals(id)) {
               customers.remove(customer);
           }
       }
   }
   
   public Customer getCustomerById(String id) {
        for (Customer customer : customers) {
           if (customer.getId().equals(id)) {
                return customer;
           }
        }
       return new Customer();
   }
   
   public int getCustomerId(String securityCred) {
       for (Customer customer : customers) {
           if (customer.getSecurityCred().equals(securityCred)) {
               return Integer.parseInt(customer.getId());
           }
       }
       return 0;
   }
   
   public String editCustomerDetails(String id, String firstName, String secondName, String address, String email, String password, String securityCred) {
       String msg = "";
       Customer customerToEdit = this.getCustomerById(id);
       if (customerToEdit.equals(null)) {
           msg = "Customer do not exist";
       } else {
           if (!firstName.equals(null)) {
               customerToEdit.setFirstName(firstName);
           } else if (!secondName.isEmpty()) {
               customerToEdit.setSecondName(secondName);
           } else if (!email.isEmpty()) {
               customerToEdit.setEmail(email);
           } else if (!address.isEmpty()) {
               customerToEdit.setAddress(address);
           } else if (!password.isEmpty()) {
               customerToEdit.setPassword(password);
           } else if (!securityCred.isEmpty()) {
               customerToEdit.setSecurityCred(securityCred);
           }
           msg = "Customer's details has been updated";
       }
       return msg;
   }
   
   public boolean updateCustomer(final Customer customer){
       for(int i = 0; i < getCustomers().size(); i++){
           Customer c = getCustomers().get(i);
           if(c.getId().equals(customer.getId())){
               customers.set(i, customer);
               return true;
           }
       }
       return false;
   }
}
