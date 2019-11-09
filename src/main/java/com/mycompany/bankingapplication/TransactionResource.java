/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankingapplication;
import com.mycompany.bankingapplication.Objects.Account;
import com.mycompany.bankingapplication.Objects.Customer;
import com.mycompany.bankingapplication.Objects.CustomersDataService;
import com.mycompany.bankingapplication.Objects.Transaction;
import com.mycompany.bankingapplication.Services.AccountService;
import com.mycompany.bankingapplication.Services.TransactionService;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam; 
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

/**
 *
 * @author yo
 */
@Path("/transaction")
public class TransactionResource {
    // TransactionService transactionServices = new TransactionService();
    // AccountService accountServices = new AccountService();
    CustomersDataService customerServices = CustomersDataService.getInstance();
    
    @POST
    @Path("/lodgement/{IBAN}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLodgement(@HeaderParam("customerId") final String customerId, @PathParam("IBAN") final String IBAN, Transaction newTransaction) {
        Customer customer = customerServices.getCustomerById(customerId);
        if (newTransaction.getTransactionType().equalsIgnoreCase("lodgement")) {
            if (customer.getSecurityCred() != null) {
            Account account = customer.getCustomerAccountByIBAN(IBAN);
            if(account != null){
                double currentBalance = account.getBalance();
                double transactionAmount = newTransaction.getTransactionAmt();
                double postTransactionAmount = currentBalance + transactionAmount;
                int currentTransactionSize = account.getTransactions().size();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
                LocalDateTime now = LocalDateTime.now();
                String transactionDateTime = dtf.format(now);
                String accountId = account.getId();
                
                if (currentTransactionSize > 0) {
                    String newId = Integer.toString(currentTransactionSize + 1);
                    newTransaction.setTransactionId(newId);
                } else if (currentTransactionSize == 0) {
                    newTransaction.setTransactionId("1");
                }
                
                newTransaction.setAccountId(accountId);
                newTransaction.setPostTransactionAmt(postTransactionAmount);
                newTransaction.setTransferDate(transactionDateTime);
                account.setBalance(postTransactionAmount);
                account.addTransaction(newTransaction);
                return Response.status(Response.Status.OK).entity(account).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Account Not Found").build();
          }
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Only lodgement transaction is allowed").build();
    }
    
    @POST
    @Path("/withdraw/{IBAN}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createWithdrawal(@HeaderParam("customerId") final String customerId, @PathParam("IBAN") final String IBAN, Transaction newTransaction) {
        Customer customer = customerServices.getCustomerById(customerId);
        if (newTransaction.getTransactionType().equalsIgnoreCase("withdrawal")) {
            if (customer.getSecurityCred() != null) {
            Account account = customer.getCustomerAccountByIBAN(IBAN);
            if(account != null){
                double currentBalance = account.getBalance();
                double transactionAmount = newTransaction.getTransactionAmt();
                double postTransactionAmount = currentBalance - transactionAmount;
                int currentTransactionSize = account.getTransactions().size();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
                LocalDateTime now = LocalDateTime.now();
                String transactionDateTime = dtf.format(now);
                String accountId = account.getId();
                
                if (currentTransactionSize > 0) {
                    String newId = Integer.toString(currentTransactionSize + 1);
                    newTransaction.setTransactionId(newId);
                } else if (currentTransactionSize == 0) {
                    newTransaction.setTransactionId("1");
                }
                
                newTransaction.setAccountId(accountId);
                newTransaction.setPostTransactionAmt(postTransactionAmount);
                newTransaction.setTransferDate(transactionDateTime);
                account.setBalance(postTransactionAmount);
                account.addTransaction(newTransaction);
                return Response.status(Response.Status.OK).entity(account).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Account Not Found").build();
          }
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Only withdrawal transaction is allowed").build();
    }
    
    
    // Transfer to another account
    /*
       Loops througj account 
    */
    /*
     @Path("/transfer/{IBAN}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
        public Response createWithdrawal(@HeaderParam("customerId") final String customerId, @PathParam("IBAN") final String IBAN, Transaction newTransaction) {

    
    */
    @Path("/transfer/{IBAN}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response transferToAccount(@HeaderParam("customerId") final String customerId, @PathParam("IBAN") final String IBAN, Transaction newTransaction){
        Customer sender = customerServices.getCustomerById(customerId);
        Customer reciever = customerServices.getCustomerById(customerId);
        if (newTransaction.getTransactionType().equalsIgnoreCase("transfer")) {
            if (sender.getSecurityCred() != null) {
                Account account = sender.getCustomerAccountByIBAN(IBAN);
                Account recieverAccount = reciever.getCustomerAccountByIBAN(IBAN);
                if(account != null){
                    double currentBalance = account.getBalance();
                    double transactionAmount = newTransaction.getTransactionAmt();
                    double postTransactionAmount ;
                    int currentTransactionSize = account.getTransactions().size();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
                    LocalDateTime now = LocalDateTime.now();
                    String transactionDateTime = dtf.format(now);
                    String accountId = account.getId();
                    
                    double rv_currentBalance = recieverAccount.getBalance();
                    double rv_postTransactionAmount ;
                    int rv_currentTransactionSize = recieverAccount.getTransactions().size();
                    String rv_accountId = recieverAccount.getId();
                    
                    if(recieverAccount != null){
                        
                        postTransactionAmount = currentBalance - transactionAmount ;
                        rv_postTransactionAmount = rv_currentBalance - transactionAmount;
                        
                        if (currentTransactionSize > 0) {
                          String newId = Integer.toString(currentTransactionSize + 1);
                            newTransaction.setTransactionId(newId);
                        } else if (currentTransactionSize == 0) {
                            newTransaction.setTransactionId("1");
                        }
                        
                        if (rv_currentTransactionSize > 0) {
                          String newId = Integer.toString(rv_currentTransactionSize + 1);
                            newTransaction.setTransactionId(newId);
                        } else if (rv_currentTransactionSize == 0) {
                            newTransaction.setTransactionId("1");
                        }
                        
                        newTransaction.setAccountId(accountId);
                        newTransaction.setPostTransactionAmt(postTransactionAmount);
                        newTransaction.setTransferDate(transactionDateTime);
                        account.setBalance(postTransactionAmount);
                        account.addTransaction(newTransaction);
                        
                     
                        newTransaction.setAccountId(rv_accountId);
                        newTransaction.setPostTransactionAmt(rv_postTransactionAmount);
                        newTransaction.setTransferDate(transactionDateTime);
                        recieverAccount.setBalance(rv_postTransactionAmount);
                        recieverAccount.addTransaction(newTransaction);
                        
                        return Response.status(Response.Status.OK).entity(account).build();
                       
        
                    }
                    return Response.status(Response.Status.NOT_FOUND).entity("Reciever Account Not Found").build();
                 
                 }
                
                 return Response.status(Response.Status.NOT_FOUND).entity("Account Not Found").build();
            }
            
             return Response.status(Response.Status.BAD_REQUEST).entity("Security error").build();
        }
        
        return Response.status(Response.Status.BAD_REQUEST).entity("Oops! Something went wrong").build();
    }
}
