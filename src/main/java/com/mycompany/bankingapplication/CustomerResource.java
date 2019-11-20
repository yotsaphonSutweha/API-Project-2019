/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankingapplication;
import com.mycompany.bankingapplication.Objects.Account;
import com.mycompany.bankingapplication.Objects.Customer;
import com.mycompany.bankingapplication.Objects.CustomersDataService;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam; 
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam; 
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.CookieParam;
/**
 *
 * @author yo
 */
@Path("/customer")
public class CustomerResource {
    CustomersDataService custOp = CustomersDataService.getInstance();
    CustomersDataService customers = CustomersDataService.getInstance();
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Customer customer){
        String email = customer.getEmail();
        String password = customer.getPassword();
        Customer loginCustomer = customers.getCustomerByEmail(email);
        if((!loginCustomer.equals(null)) && (loginCustomer.getPassword().equals(password))){
            String loginId = loginCustomer.getId();
            NewCookie cookie = new NewCookie("customerId", loginId, "/", "", "comment", 30000, false);
            return Response.status(Response.Status.OK).cookie(cookie).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Customer Does Not Exist").build();
    }
    
    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@CookieParam("customerId") Cookie cookie){
        if(cookie != null){
            NewCookie mycookie= new NewCookie("customerId", null, "/", "", NewCookie.DEFAULT_VERSION, null, 0, new Date(), false, false);
            return Response.status(Response.Status.OK).cookie(mycookie).build();
        }
        return Response.status(Response.Status.OK).entity("OK - No session").build();
    }
    
   
    // for general user
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(Customer newCustomer) {
        if (newCustomer != null ) {
            customers.addCustomer(newCustomer);
            return Response.status(Response.Status.OK).entity(newCustomer).build();
        } 
        return Response.status(Response.Status.NO_CONTENT).entity("Inputs required to create new customer").build();

    }
   
    // for admin
    @DELETE
    @Path("/customers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCustomer(Customer customer) {
        String firstName = "";
        if (customer != null ) {
            firstName = customer.getFirstName();
            customers.deleteCustomerbyName(firstName);
            return Response.status(Response.Status.OK).build();
        } 
        return Response.status(Response.Status.NO_CONTENT).entity("Inputs required to delete a customer").build();
    }
    
    // for admin only
    @DELETE
    @Path("/customers/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCustomerById(@PathParam("id") String id) {
        if (!id.isEmpty()) {
           customers.deleteCustomerById(id);
           return Response.status(Response.Status.OK).build();
        } 
        return Response.status(Response.Status.NO_CONTENT).entity("Id required to delete a customer").build();
    }
    
    // for modifying specific customer's details
    @POST
    @Path("/editdetails")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editCustomerDetails(@CookieParam("customerId") Cookie cookie, Customer customer) {
        String id = cookie.getValue();
        try {
            if (customer != null) {
                String firstName = customer.getFirstName();
                String secondName = customer.getSecondName();
                String address = customer.getAddress();
                String email = customer.getEmail();
                String password = customer.getPassword();
                String securityCred = customer.getSecurityCred();
                customers.editCustomerDetails(id, firstName, secondName, address, email, password, securityCred);
                Customer updatedCustomer = customers.getCustomerById(id);
                return Response.status(Response.Status.OK).entity(updatedCustomer).build();
            } 
        } catch(Exception e) {
                System.out.println(e);
        }
        return Response.status(Response.Status.NO_CONTENT).entity("Inputs required to edit a customer").build();
    }
 
    // For general use
    @GET
    @Path("/customers")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Customer> getCustomers() {
        ArrayList<Customer> existingCustomers = customers.getCustomers();
        if (existingCustomers.isEmpty()) {
             return null;
        }
        return existingCustomers;
    }
    
    // For specific customer details, get the customer based on ID
    @GET
    @Path("/details")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response  getCustomerById(@CookieParam("customerId") Cookie cookie) {
        String id = cookie.getValue();
        if (id.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity("Please login").build();
        }
        Customer customerBasedOnId = customers.getCustomerById(id);
        return Response.status(Response.Status.OK).entity(customerBasedOnId).build();
    }
    
    
}
