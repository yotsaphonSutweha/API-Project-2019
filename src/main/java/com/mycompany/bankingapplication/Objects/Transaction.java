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
    private int accountId;
    private String transactionId;
    private String transactionType;
    private String transferDate;
    private String description;
    private double postTransactionAmt;
    private double transactionAmt;
    private String senderName;
    private String transferIBAN;
    
    public Transaction() {
        
    }

    public Transaction(int accountId, String transactionId, String transactionType, String transferDate, String description, double postTransactionAmt, double transactionAmt) {
        this.accountId = accountId;
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.transferDate = transferDate;
        this.description = description;
        this.postTransactionAmt = postTransactionAmt;
        this.transactionAmt = transactionAmt;
    }

    public String getTransferIBAN() {
        return transferIBAN;
    }

    public void setTransferIBAN(String transferIBAN) {
        this.transferIBAN = transferIBAN;
    }
    
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

  
    public double getPostTransactionAmt() {
        return postTransactionAmt;
    }

    public void setPostTransactionAmt(double postTransactionAmt) {
        this.postTransactionAmt = postTransactionAmt;
    }
    
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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
