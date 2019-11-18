/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankingapplication.Services;

import com.mycompany.bankingapplication.Objects.Account;
import com.mycompany.bankingapplication.Objects.Transaction;
import com.mycompany.bankingapplication.Objects.Customer;
import com.mycompany.bankingapplication.Objects.CustomersDataService ;
import com.mycompany.bankingapplication.Service.AccountService;

import java.util.ArrayList;

/**
 *
 * @author yo
 */
public class TransactionService {
    ArrayList<Transaction> transactions = new ArrayList<>();
    private static TransactionService allTransactions = new TransactionService();
    CustomersDataService customers = CustomersDataService.getInstance();
    
    public static TransactionService getInstance() {
       return allTransactions;
   }
    
   public ArrayList<Transaction> getTransactions(){
       return transactions;
   } 
   
   public void lodgement(Transaction transaction, Account account){
       // For refactoring
   }
   
   public void withdraw(Transaction transaction, Account account){
       // For refactoring
   }
   
   public ArrayList<Transaction> getTransactionsByAccount(String customerId, String accountId){
       
        ArrayList<Transaction> transactions = new ArrayList<>();
        ArrayList<Account> accounts =  new AccountService().getAllCustomerAccounts(customerId) ;
      
        for(int i= 0 ; i<accounts.size() ; i++ ){
            if(accounts.get(i).getId().equals(accountId)){
                transactions = accounts.get(i).getTransactions() ;
            }
        }
       return transactions;
   }
   
}
