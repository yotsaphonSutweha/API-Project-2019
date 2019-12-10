/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankingapplication.Services;

import com.mycompany.bankingapplication.Objects.Account;
import com.mycompany.bankingapplication.Objects.Transaction;
import java.util.ArrayList;

/**
 *
 * @author yo
 */
public class TransactionService {
    ArrayList<Transaction> transactions = new ArrayList<>();
    private static TransactionService allTransactions = new TransactionService();
    
    public static TransactionService getInstance() {
       return allTransactions;
   }
    
   public ArrayList<Transaction> getTransactions(){
       return transactions;
   } 
}
