/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankingapplication.Objects;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yo
 */
@XmlRootElement(name="Account")
public class Account {
    private String id;
    private String ownerId;
    private double balance;
    private String IBAN;
    private String accountType;
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public Account() {
        
    }
    
    public Account(String id, String ownerId, double balance, String IBAN, String accountType) {
        this.id = id;
        this.ownerId = ownerId;
        this.balance = balance;
        this.IBAN = IBAN;
        this.accountType = accountType;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
    
    public void addTransaction(final Transaction transaction){
        transactions.add(transaction);
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    
    public void setOwnerId(final String ownerId){
        this.ownerId = ownerId;
    }
    
    public String getOwnerId(){
        return ownerId;
    }
}
