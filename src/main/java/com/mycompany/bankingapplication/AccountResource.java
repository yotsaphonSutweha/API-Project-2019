/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankingapplication;

import com.mycompany.bankingapplication.Objects.Account;
import com.mycompany.bankingapplication.Objects.Customer;
import com.mycompany.bankingapplication.Objects.Customers;
import com.mycompany.bankingapplication.Objects.CustomersDataService;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author hassan
 */
@Path("/account")
public class AccountResource {
    
    @GET
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Account> getAllAccounts(@HeaderParam("customerId") final String adminId){
        List<Account> accounts = new ArrayList();
        for(Customer customer : CustomersDataService.getInstance().getCustomers()){
            accounts.addAll(customer.getAccounts());
        }
        return accounts;
    }
    
    @GET
    @Path("/{IBAN}")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getSpecificAccountByIBAN(@HeaderParam("customerId") final String adminId, 
            @PathParam("IBAN") final String IBAN){
        for(Customer customer : CustomersDataService.getInstance().getCustomers()){
            Account account = customer.getCustomerAccountByIBAN(IBAN);
            if(account != null){
                return Response.status(Response.Status.OK).entity(account).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Account not found").build();
    }
    
    @GET
    @Path("/current")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Account> getAllCustomerAccounts(@HeaderParam("customerId") final String customerId){
        return CustomersDataService.getInstance()
                        .getCustomerById(customerId)
                        .getAccounts();
    }
    
    @GET
    @Path("/current/{IBAN}")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getSpecificCustomerAccount(@HeaderParam("customerId") final String customerId, @PathParam("IBAN") final String IBAN){
        Customer customer = CustomersDataService.getInstance().getCustomerById(customerId);
        if(customer.getSecurityCred()!= null){
            System.out.println("it should be empty");
            Account account = customer.getCustomerAccountByIBAN(IBAN);
            if(account != null){
                return Response.status(Response.Status.OK).entity(account).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Account not found").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
    }
    
    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(@HeaderParam("customerId") final String customerId, final Account account){
        Customer customer = CustomersDataService.getInstance().getCustomerById(customerId);
        if(customer.getSecurityCred()!= null){
            customer.addAccount(account);
            CustomersDataService.getInstance().updateCustomer(customer);
            return Response.status(Response.Status.OK).entity(account).build();
        }
         return Response.status(Response.Status.NOT_FOUND).entity("Customer not found" + customerId).build();
    }
    
    @PUT
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAccount(@HeaderParam("customerId") final String customerId, final Account account){
        Customer customer = CustomersDataService.getInstance().getCustomerById(customerId);
        if(customer.getSecurityCred()!= null){
            ArrayList<Account> accounts = customer.getAccounts();
            for(int i = 0; i < accounts.size(); i++){
                Account a = accounts.get(i);
                if(a.getIBAN().equals(account.getIBAN())){
                    accounts.set(i, account);
                    customer.setAccounts(accounts);
                    CustomersDataService.getInstance().updateCustomer(customer);
                    return Response.status(Response.Status.OK).entity(account).build();
                }
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Account not found").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
    }
}
