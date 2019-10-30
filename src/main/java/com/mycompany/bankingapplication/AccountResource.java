/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankingapplication;

import com.mycompany.bankingapplication.Objects.Account;
import com.mycompany.bankingapplication.Objects.Customer;
import com.mycompany.bankingapplication.Objects.Customers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author hassan
 */
@Path("/account")
public class AccountResource {
    
    @GET
    public List<Account> getAllAccounts(@HeaderParam("customerId") final String adminId){
        List<Account> accounts = new ArrayList();
        for(Customer customer : Customers.getInstance().getCustomerList()){
            accounts.addAll(customer.getAccounts());
        }
        return accounts;
    }
    
    @GET
    @Path("/{IBAN}")
    public Account getSpecificAccountByIBAN(@HeaderParam("customerId") final String adminId, 
            @PathParam("IBAN") final String IBAN){
        for(Customer customer : Customers.getInstance().getCustomerList()){
            Account account = customer.getCustomerAccountByIBAN(IBAN);
            if(account != null){
                return account;
            }
        }
        throw new WebApplicationException("Account not found", Response.Status.NOT_FOUND);
    }
    
    @GET
    @Path("/current")
    public List<Account> getAllCustomerAccounts(@HeaderParam("customerId") final String customerId){
        return Customers.getInstance()
                        .getCustomerById(customerId)
                        .getAccounts();
    }
    
    @GET
    @Path("/current/{IBAN}")
    public Account getSpecificCustomerAccount(@HeaderParam("customerId") final String customerId, @PathParam("IBAN") final String IBAN){
        Customer customer = Customers.getInstance().getCustomerById(customerId);
        if(customer != null){
            Account account = customer.getCustomerAccountByIBAN(IBAN);
            if(account != null){
                return account;
            }
            throw new WebApplicationException("Account not found", Response.Status.NOT_FOUND);
        }
        throw new WebApplicationException("Customer not found", Response.Status.NOT_FOUND);
    }
    
    @POST
    public void createAccount(@HeaderParam("customerId") final String customerId, final Account account){
        Customer customer = Customers.getInstance().getCustomerById(customerId);
        if(customer != null){
            customer.addAccount(account);
            Customers.getInstance().updateCustomer(customer);
            return;
        }
        throw new WebApplicationException("Customer not found", Response.Status.NOT_FOUND);
    }
    
    @PUT
    public void updateAccount(@HeaderParam("customerId") final String customerId, final Account account){
        Customer customer = Customers.getInstance().getCustomerById(customerId);
        if(customer != null){
            ArrayList<Account> accounts = customer.getAccounts();
            for(int i = 0; i < accounts.size(); i++){
                Account a = accounts.get(i);
                if(a.getIBAN().equals(account.getIBAN())){
                    accounts.set(i, account);
                    customer.setAccounts(accounts);
                    Customers.getInstance().updateCustomer(customer);
                    return;
                }
            }
            throw new WebApplicationException("Account not found", Response.Status.NOT_FOUND);
        }
        throw new WebApplicationException("Customer not found", Response.Status.NOT_FOUND);
    }
}
