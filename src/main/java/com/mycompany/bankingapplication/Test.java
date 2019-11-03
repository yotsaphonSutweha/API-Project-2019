/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankingapplication;

import com.mycompany.bankingapplication.Objects.Account;
import com.mycompany.bankingapplication.Objects.Customer;
import com.mycompany.bankingapplication.Objects.Customers;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam; 
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author hassan
 */
@Path("/test")
public class Test {
//    Customer newCustomer = new Customer();
//    ArrayList<Customer> newCustomers = new ArrayList<>();
//    
    
    @GET
    @Produces("application/json")
    public String setupTestObjects(){
        Customer customer = new Customer("1", "Yo", "Suts", "Abbey Street", "yo@gmail.com", "ajkshdakshd",  "110L");
        Account account = new Account("1", 100, "IBAN", "Current");
        customer.addAccount(account);
        Customers.getInstance().addCustomer(customer);
        return "test setup";
    }
    
    @GET
    @Path("/{id}")
    public String test(@PathParam("id") int id){
        System.out.println("was it called");
        return "Hello World " + id;
    }
   
    @GET
    @Path("/sayHello")
    public String test(){
        return "Hello World";
    }
    
    @GET
    @Path("/echo")
    public Response getEchoMsg(@QueryParam("message") String msg) {
        return Response.ok("Your message was: " + msg).build();
    }
    
    // Test mock getting a mock customer
    @GET
    @Path("/mockcustomer")
    public Customer getCustomer() {
        return new Customer("1", "Yo", "Suts", "Abbey Street", "yo@gmail.com", "ajkshdakshd",  "110L");
    }
    
    
    
//    @POST
//    @Path("/createcustomer")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.TEXT_PLAIN)
//    public String createCustomer(Customer customer) {
//        newCustomers.add(customer);
//        return "Done";
//    }

}
