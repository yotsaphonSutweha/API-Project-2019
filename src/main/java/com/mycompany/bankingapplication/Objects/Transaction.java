/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankingapplication.Objects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yo
 */
@XmlRootElement(name="Transaction")
public class Transaction {
    private String transactionId;
    private String transactionType;
    private String transferDate;
    private String description;
    private double currentBalance;
    private double transactionAmt;
   
    
    public Transaction() {
        
    }

    public Transaction(String transactionId, String transactionType, String transferDate, String description, double currentBalance, double transactionAmt) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.transferDate = transferDate;
        this.description = description;
        this.currentBalance = currentBalance;
        this.transactionAmt = transactionAmt;
        
    }
    

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public double getTransactionAmt() {
        return transactionAmt;
    }

    public void setTransactionAmt(double transactionAmt) {
        this.transactionAmt = transactionAmt;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    
    
}
