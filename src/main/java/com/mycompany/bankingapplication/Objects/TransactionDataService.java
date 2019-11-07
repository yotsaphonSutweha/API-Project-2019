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
public class TransactionDataService {
    ArrayList<Transaction> transactions = new ArrayList<>();
    private static TransactionDataService allTransactions = new TransactionDataService();
    
    public static TransactionDataService getInstance() {
       return allTransactions;
   }
    
    
   
   public ArrayList<Transaction> getTransactions(){
       return transactions;
   } 
   
   public void Lodgement(Transaction transaction, Account account){
       double currentBalance = account.getBalance();
       double transactionAmount = transaction.getTransactionAmt();
       currentBalance = currentBalance + transactionAmount;
       account.setBalance(currentBalance);
       transactions.add(transaction);
       account.setTransactions(transactions);
   }
   
   public void Withdraw(Transaction transaction, Account account){
       double currentBalance = account.getBalance();
       double transactionAmount = transaction.getTransactionAmt();
       currentBalance = currentBalance - transactionAmount;
       account.setBalance(currentBalance);
       transactions.add(transaction);
       account.setTransactions(transactions);
   }
}
