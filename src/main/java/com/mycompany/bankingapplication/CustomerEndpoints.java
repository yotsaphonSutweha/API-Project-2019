/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankingapplication;
import com.mycompany.bankingapplication.Objects.Customer;
import com.mycompany.bankingapplication.Objects.CustomersDataService;
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
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam; 
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 *
 * @author yo
 */
@Path("/customer")
public class CustomerEndpoints {
    CustomersDataService custOp = CustomersDataService.getInstance();
    
    @POST
    @Path("/createcustomer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createCustomer(Customer newCustomer) {
        newCustomer.setAccounts(null);
        return custOp.addCustomer(newCustomer);
    }
    
    @POST
    @Path("/deletecustomer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteCustomer(Customer customer) {
        String firstName = customer.getFirstName();
        custOp.deleteCustomerbyName(firstName);
    }
    
    @POST
    @Path("/editcustomer/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String editCustomerDetails(@PathParam("id") String id, Customer customer) {
        String firstName = customer.getFirstName();
        String secondName = customer.getSecondName();
        String address = customer.getAddress();
        String email = customer.getEmail();
        String password = customer.getPassword();
        String securityCred = customer.getSecurtityCred();
        return custOp.editCustomerDetails(id, firstName, secondName, address, email, password, securityCred);
    }
 
    @GET
    @Path("/getcustomers")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Customer>  getCustomers() {
        return custOp.getCustomers();
    }
    
    @GET
    @Path("/getcustomer/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer  getCustomerById(@PathParam("id") String id) {
        return custOp.getCustomerById(id);
    }
    
    
}
