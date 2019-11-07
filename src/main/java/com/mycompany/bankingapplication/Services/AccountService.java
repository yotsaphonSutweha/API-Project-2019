/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankingapplication.Services;

import com.mycompany.bankingapplication.Objects.Account;
import com.mycompany.bankingapplication.Objects.Customer;
import com.mycompany.bankingapplication.Objects.CustomersDataService;
import java.util.ArrayList;


/**
 *
 * @author hassan
 */
public class AccountService {
    CustomersDataService customers = CustomersDataService.getInstance();
    
    public ArrayList<Account> getAllAccounts(){
        ArrayList<Account> accounts = new ArrayList();
        for(Customer customer : customers.getCustomerList()){
            accounts.addAll(customer.getAccounts());
        }
        return accounts;
     }
    
    public Account getAccountByIBAN(String IBAN){
        Account account = null;
        for(Customer customer : customers.getCustomerList()){
            account = customer.getCustomerAccountByIBAN(IBAN);
            if(account != null){
                return account;
            }
        }
        return null;
    }
    
    public ArrayList<Account> getAllCustomerAccounts(final String id){
        return customers.getCustomerById(id).getAccounts();
    }
    
    public Account updateAccount(Customer customer, Account account){
        ArrayList<Account> accounts = customer.getAccounts();
        for(int i = 0; i < accounts.size(); i++){
            Account a = accounts.get(i);
            if(a.getIBAN().equals(account.getIBAN())){
                accounts.set(i, account);
                customer.setAccounts(accounts);
                customers.editCustomerDetails(customer);
                return account;
            }
        }
        return null;
    }
    
    public Account deleteAccount(Customer customer, String IBAN){
        ArrayList<Account> accounts = customer.getAccounts();
        for(int i = 0; i < accounts.size(); i++){
            if(accounts.get(i).getIBAN().equals(IBAN)){
                Account removedAccount = accounts.remove(i);
                customer.setAccounts(accounts);
                customers.editCustomerDetails(customer);
                return removedAccount;
            }
        }
        return null;
    }
}
