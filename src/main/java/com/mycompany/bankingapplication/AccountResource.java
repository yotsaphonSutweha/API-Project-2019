/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankingapplication;

import com.mycompany.bankingapplication.Helpers.DebugMapper;
import com.mycompany.bankingapplication.Objects.Account;
import com.mycompany.bankingapplication.Objects.Customer;
import com.mycompany.bankingapplication.Services.CustomersService;
import com.mycompany.bankingapplication.Services.AccountService;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author hassan
 */
@Path("/account")
public class AccountResource {
    
    AccountService service = new AccountService();
    CustomersService customers = CustomersService.getInstance();
    ResourceConfig config = new ResourceConfig()
        .register(DebugMapper.class);
    
    @GET
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllAccounts(@CookieParam("customerId") final Cookie cookie){
         if(cookie == null)
                return Response.status(Response.Status.NOT_FOUND).build();
        String adminId = cookie.getValue();
        Customer admin = customers.getCustomerById(adminId);
        if(admin.getPrivilages()){
            ArrayList<Account> accounts = service.getAllAccounts();
            GenericEntity<ArrayList<Account>> entity = new GenericEntity<ArrayList<Account>>(accounts){};
            return Response.status(Response.Status.OK).entity(entity).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("You must be an Admin to view all accounts").build();
    }
    
    @GET
    @Path("/{IBAN}")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response accountByIBAN(@CookieParam("customerId") final Cookie cookie, 
            @PathParam("IBAN") final String IBAN){
        if(cookie == null)
                return Response.status(Response.Status.NOT_FOUND).build();
        String adminId = cookie.getValue();
        Customer admin = customers.getCustomerById(adminId);
        Account account = service.getAccountByIBAN(IBAN);
        //must be either an admin or the owner of account to view
        if(admin.getPrivilages() == true || admin.getId() == account.getOwnerId()){
            if(account != null){
                return Response.status(Response.Status.OK).entity(account).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Account not found").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("You must be an Admin to view another users account").build();
    }
    
    // For specific customer, get the accounts on specific customer
    @GET
    @Path("/current")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response customerAccounts(@CookieParam("customerId") Cookie cookie){
        try{
            if(cookie == null)
                return Response.status(Response.Status.NOT_FOUND).build();
            String customerId = cookie.getValue();
            ArrayList<Account> accounts = service.getAllCustomerAccounts(customerId);
            if(!accounts.isEmpty()){
                GenericEntity<ArrayList<Account>> entity = new GenericEntity<ArrayList<Account>>(accounts){};
                return Response.status(Response.Status.OK).entity(entity).build();
            }
            return Response.status(Response.Status.NO_CONTENT).build();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // For specific customer to get specific account by IBAN
    @GET
    @Path("/current/{IBAN}")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getSpecificCustomerAccount(@CookieParam("customerId") final Cookie cookie, @PathParam("IBAN") final String IBAN){
         if(cookie == null)
                return Response.status(Response.Status.NOT_FOUND).build();
        String customerId = cookie.getValue();
        Customer customer = customers.getCustomerById(customerId);
        System.out.print(customer.getFirstName());
        if(customer.getSecurityCred() != null){
            Account account = customer.getCustomerAccountByIBAN(IBAN);
            if(account != null){
                return Response.status(Response.Status.OK).entity(account).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Account not found").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
    }
    
    // For specific customer to create a new account
    @POST
    // @Path("/{customerId}")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(@CookieParam("customerId") final String customerId, final Account account){
         if(customerId == null)
                return Response.status(Response.Status.NOT_FOUND).build();
        Customer customer = customers.getCustomerById(customerId);
        if(customer.getSecurityCred() != null){
            if (customer.getAccounts().size() == 0) {
                account.setId(1);
            } else if (customer.getAccounts().size() > 0) {
                int newId = customer.getAccounts().size() + 1;
                account.setId(newId);
            }
            account.setOwnerId(customer.getId());
            customer.addAccount(account);
            customers.editCustomerDetails(customer);
            return Response.status(Response.Status.OK).entity(account).build();
        }
         return Response.status(Response.Status.NOT_FOUND).entity("Customer not found" + customerId).build();
    }
    
    // For specific customer to update the existing account
    @PUT
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAccount(@CookieParam("customerId") final String customerId, final Account newAccount){
         if(customerId == null)
                return Response.status(Response.Status.NOT_FOUND).build();
        Customer customer = customers.getCustomerById(customerId);
        if(customer.getSecurityCred() != null){
            Account updatedAccount = service.updateAccount(customer, newAccount);
            if(updatedAccount != null){
                return Response.status(Response.Status.OK).entity(newAccount).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Account not found").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
    }
    
    // For specific customer to delete the existing account
    @DELETE
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAccount(@CookieParam("customerId") final String customerId, final Account account){
         if(customerId == null)
                return Response.status(Response.Status.NOT_FOUND).build();
        Customer customer = customers.getCustomerById(customerId);
        if(customer.getSecurityCred() != null){
            Account deletedAccount = service.deleteAccount(customer, account.getIBAN());
            if(deletedAccount != null){
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Account not found").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
    }
}
