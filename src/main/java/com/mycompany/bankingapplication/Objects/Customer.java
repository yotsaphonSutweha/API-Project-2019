/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankingapplication.Objects;

import java.util.ArrayList;
import java.util.Optional;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yo
 */

@XmlRootElement
public class Customer {
    private String id;
    private String firstName;
    private String secondName;
    private String address;
    private String email;
    private String password;
    private String securtityCred;
    private ArrayList<Account> accounts;
    //not really a customer but for this project it will do fine
    private boolean isAdmin;
            
    public Customer() {
        this.accounts = new ArrayList();
        this.isAdmin = false;
    } 

    public Customer(String id, String firstName, String secondName, String address, String email, String password, String securtityCred) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.address = address;
        this.email = email;
        this.password = password;
        this.securtityCred = securtityCred;
        this.accounts = new ArrayList();
        this.isAdmin = false;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
    
    public void addAccount(final Account account){
        accounts.add(account);
    }
    
    public Account getCustomerAccountByIBAN(final String IBAN){
        for(Account account : accounts){
            if(account.getIBAN().equals(IBAN)){
                return account;
            }
        }
        return null;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurtityCred() {
        return securtityCred;
    }

    public void setSecurtityCred(String securtityCred) {
        this.securtityCred = securtityCred;
    } 
    
    public boolean getPrivilages(){
        return isAdmin;
    }
    
    public void setPrivilages(boolean isAdmin){
        this.isAdmin = isAdmin;
    }
}
