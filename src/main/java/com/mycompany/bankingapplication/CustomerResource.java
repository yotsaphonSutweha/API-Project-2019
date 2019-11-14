/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankingapplication;
import com.mycompany.bankingapplication.Objects.Customer;
import com.mycompany.bankingapplication.Objects.CustomersDataService;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam; 
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/**
 *
 * @author yo
 */
@Path("/customer")
public class CustomerResource {
    CustomersDataService custOp = CustomersDataService.getInstance();
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Customer customer){
        String securityCred = customer.getSecurityCred();
        Customer loginCustomer = CustomersDataService.getInstance().getCustomerBySecurityCred(securityCred);
        if(!loginCustomer.equals(null)){
            String loginId = loginCustomer.getId();
            NewCookie cookie = new NewCookie("customerId", loginId);
            return Response.status(Response.Status.OK).cookie(cookie).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Customer Does Not Exist").build();
    }
    
    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@CookieParam("customerId") Cookie cookie){
        if(cookie != null){
            NewCookie newCookie = new NewCookie(cookie, null, 0, false);
            return Response.status(Response.Status.OK).cookie(newCookie).build();
        }
        return Response.status(Response.Status.OK).entity("OK - No session").build();
    }
    
    @POST
    @Path("/createcustomer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(Customer newCustomer) {
        if (newCustomer != null ) {
            CustomersDataService.getInstance().addCustomer(newCustomer);
            return Response.status(Response.Status.OK).entity(newCustomer).build();
        } 
        return Response.status(Response.Status.NO_CONTENT).entity("Inputs required to create new customer").build();

    }
   
    
    @POST
    @Path("/deletecustomer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCustomer(Customer customer) {
        String firstName = "";
        if (customer != null ) {
            firstName = customer.getFirstName();
            CustomersDataService.getInstance().deleteCustomerbyName(firstName);
            return Response.status(Response.Status.OK).build();
        } 
        return Response.status(Response.Status.NO_CONTENT).entity("Inputs required to delete a customer").build();
    }
    
    @POST
    @Path("/editcustomer/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editCustomerDetails(@PathParam("id") String id, Customer customer) {
        if (customer != null) {
            String firstName = customer.getFirstName();
            String secondName = customer.getSecondName();
            String address = customer.getAddress();
            String email = customer.getEmail();
            String password = customer.getPassword();
            String securityCred = customer.getSecurityCred();
            CustomersDataService.getInstance().editCustomerDetails(id, firstName, secondName, address, email, password, securityCred);
            Customer updatedCustomer = CustomersDataService.getInstance().getCustomerById(id);
            return Response.status(Response.Status.OK).entity(updatedCustomer).build();
        } 
        return Response.status(Response.Status.NO_CONTENT).entity("Inputs required to edit a customer").build();
    }
 
    @GET
    @Path("/getcustomers")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Customer> getCustomers() {
        ArrayList<Customer> existingCustomers = CustomersDataService.getInstance().getCustomers();
        if (existingCustomers.isEmpty()) {
             return null;
        }
        return existingCustomers;
    }
    
    @GET
    @Path("/getcustomer/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response  getCustomerById(@PathParam("id") String id) {
        if (id.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity("customer id required").build();
        }
        Customer customerBasedOnId = CustomersDataService.getInstance().getCustomerById(id);
        return Response.status(Response.Status.OK).entity(customerBasedOnId).build();
    }
    
    
}
