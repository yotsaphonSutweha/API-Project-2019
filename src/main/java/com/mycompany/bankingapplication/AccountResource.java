/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankingapplication;

import com.mycompany.bankingapplication.Objects.Account;
import com.mycompany.bankingapplication.Objects.Customer;
import com.mycompany.bankingapplication.Objects.CustomersDataService;
import com.mycompany.bankingapplication.Services.AccountService;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author hassan
 */
@Path("/account")
public class AccountResource {
    
    private final CustomersDataService customers = CustomersDataService.getInstance();
    private final AccountService service = new AccountService();
    @GET
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllAccounts(@HeaderParam("customerId") final String adminId){
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
    public Response AccountByIBAN(@HeaderParam("customerId") final String adminId, 
            @PathParam("IBAN") final String IBAN){
        Customer admin = customers.getCustomerById(adminId);
        if(admin.getPrivilages() == true){
            Account account = service.getAccountByIBAN(IBAN);
            if(account != null){
                return Response.status(Response.Status.OK).entity(account).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Account not found").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("You must be an Admin to view another users account").build();
    }
    
    @GET
    @Path("/current")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response customerAccounts(@HeaderParam("customerId") final String customerId){
        ArrayList<Account> accounts = service.getAllCustomerAccounts(customerId);
        if(accounts.isEmpty()){
            GenericEntity<ArrayList<Account>> entity = new GenericEntity<ArrayList<Account>>(accounts){};
            return Response.status(Response.Status.OK).entity(entity).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    @GET
    @Path("/current/{IBAN}")
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getSpecificCustomerAccount(@HeaderParam("customerId") final String customerId, @PathParam("IBAN") final String IBAN){
        Customer customer = customers.getCustomerById(customerId);
        if(customer.getSecurtityCred() != null){
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
        Customer customer = customers.getCustomerById(customerId);
        if(customer.getSecurtityCred() != null){
            account.setOwnerId(customer.getId());
            customer.addAccount(account);
            customers.editCustomerDetails(customer);
            return Response.status(Response.Status.OK).entity(account).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
    }
    
    @PUT
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAccount(@HeaderParam("customerId") final String customerId, final Account newAccount){
        Customer customer = customers.getCustomerById(customerId);
        if(customer.getSecurtityCred() != null){
            Account updatedAccount = service.updateAccount(customer, newAccount);
            if(updatedAccount != null){
                return Response.status(Response.Status.OK).entity(newAccount).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Account not found").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
    }
    
    @DELETE
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAccount(@HeaderParam("customerId") final String customerId, final String IBAN){
        Customer customer = customers.getCustomerById(customerId);
        if(customer.getSecurtityCred() != null){
            Account deletedAccount = service.deleteAccount(customer, IBAN);
            if(deletedAccount != null){
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Account not found").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
    }
}
