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
    private String transactionType;
    private String transferDate;
    private String description;
    private int preBalance;
    private int postBalance;
    
    public Transaction() {
        
    }

    public Transaction(String transactionType, String transferDate, String description, int preBalance, int postBalance) {
        this.transactionType = transactionType;
        this.transferDate = transferDate;
        this.description = description;
        this.preBalance = preBalance;
        this.postBalance = postBalance;
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

    public int getPreBalance() {
        return preBalance;
    }

    public void setPreBalance(int preBalance) {
        this.preBalance = preBalance;
    }

    public int getPostBalance() {
        return postBalance;
    }

    public void setPostBalance(int postBalance) {
        this.postBalance = postBalance;
    }
    
    
}
