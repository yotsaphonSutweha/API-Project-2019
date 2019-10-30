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
        throw new WebApplicationException(Response.Status.NOT_FOUND);
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
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }
    
    @POST
    public void createAccount(@HeaderParam("customerId") final String customerId, final Account account){
        
    }
    
    @PUT
    public void updateAccount(@HeaderParam("customerId") final String customerId, final Account account){
    
    }
}
