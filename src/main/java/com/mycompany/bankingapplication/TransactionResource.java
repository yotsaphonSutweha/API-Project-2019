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
import javax.ws.rs.CookieParam;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericEntity;

/**
 *
 * @author yo
 */
@Path("/transaction")
public class TransactionResource {
    // TransactionService transactionServices = new TransactionService();
    CustomersDataService customerServices = CustomersDataService.getInstance();
    AccountService service = new AccountService();
    
    // For specific customer to make lodgement based on IBAN
    @POST
    @Path("/lodgement/{IBAN}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLodgement(@CookieParam("customerId") final Cookie cookie, @PathParam("IBAN") final String IBAN, Transaction newTransaction) {
         if(cookie == null)
                return Response.status(Response.Status.NOT_FOUND).build();
        String customerId = cookie.getValue();
        Customer customer = customerServices.getCustomerById(customerId);
        if (newTransaction.getTransactionType().equalsIgnoreCase("lodgement")) {
            if (customer.getSecurityCred() != null) {
            Account account = customer.getCustomerAccountByIBAN(IBAN);
            if(account != null){
                double currentBalance = account.getBalance();
                double transactionAmount = newTransaction.getTransactionAmt();
                double postTransactionAmount = currentBalance + transactionAmount;
                System.out.print(postTransactionAmount);
                int currentTransactionSize = account.getTransactions().size();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
                LocalDateTime now = LocalDateTime.now();
                String transactionDateTime = dtf.format(now);
                int accountId = account.getId();
                
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
    
    // For specific customer to make withdrawal based on IBAN
    @POST
    @Path("/withdrawal/{IBAN}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createWithdrawal(@CookieParam("customerId") final Cookie cookie, @PathParam("IBAN") final String IBAN, Transaction newTransaction) {
         if(cookie == null)
                return Response.status(Response.Status.NOT_FOUND).build();
        String customerId = cookie.getValue();
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
                int accountId = account.getId();
                
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
    
    @POST
    @Path("/{IBAN}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response transactionsByIBAN(@CookieParam("customerId") Cookie cookie, @PathParam("IBAN") String currentCustomerIBAN, Transaction newTransaction){
         if(cookie == null)
                return Response.status(Response.Status.NOT_FOUND).build();
        String currentCustomerId = cookie.getValue();
        Customer currentCustomer = customerServices.getCustomerById(currentCustomerId);
        Account currentAccount = service.getAccountByIBAN(currentCustomerIBAN);
        if (currentAccount.getIBAN().equals(newTransaction.getTransferIBAN())) {
             return Response.status(Response.Status.BAD_REQUEST).entity("The two provided IBANs have the same value").build();
        } else {
            if (newTransaction.getTransactionType().equalsIgnoreCase("transfer")){
                if (currentCustomer.getSecurityCred() != null) {
                    Account transfering = service.getAccountByIBAN(newTransaction.getTransferIBAN());
                    if(currentAccount != null){
                        double transactionAmount = newTransaction.getTransactionAmt();
                        // Handle the sender's details
                        double senderCurrentBalance = currentAccount.getBalance();
                        double postTransactionAmountForSender = senderCurrentBalance - transactionAmount;
                        int currentTransactionSize = currentAccount.getTransactions().size();
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
                        LocalDateTime now = LocalDateTime.now();
                        String transactionDateTime = dtf.format(now);
                        int accountId = currentAccount.getId();
                        if (currentTransactionSize > 0) {
                            String newId = Integer.toString(currentTransactionSize + 1);
                            newTransaction.setTransactionId(newId);
                        } else if (currentTransactionSize == 0) {
                            newTransaction.setTransactionId("1");
                        }
                        newTransaction.setAccountId(accountId);
                        newTransaction.setPostTransactionAmt(postTransactionAmountForSender);
                        newTransaction.setTransferDate(transactionDateTime);
                        currentAccount.setBalance(postTransactionAmountForSender);
                        currentAccount.addTransaction(newTransaction);
                        
                        // Handle the receiver's details
                        int transferingAccountSize = transfering.getTransactions().size();
                        double receiverCurrentBalance = transfering.getBalance();
                        double postTransactionAmountForReceiver  = receiverCurrentBalance + transactionAmount;
                        Transaction receiverTransaction = new Transaction();
                        if (transferingAccountSize > 0) {
                            String newId = Integer.toString(transferingAccountSize + 1);
                            receiverTransaction.setTransactionId(newId);
                        } else if (transferingAccountSize == 0) {
                            receiverTransaction.setTransactionId("1");
                        }
                        receiverTransaction.setAccountId(transfering.getId());
                        receiverTransaction.setDescription("Received from transfer");
                        receiverTransaction.setTransactionAmt(transactionAmount);
                        receiverTransaction.setTransactionType("Transfer");
                        receiverTransaction.setPostTransactionAmt(postTransactionAmountForReceiver);
                        receiverTransaction.setTransferDate(transactionDateTime);
                        transfering.setBalance(postTransactionAmountForReceiver);
                        transfering.addTransaction(receiverTransaction);
                        return Response.status(Response.Status.OK).entity(currentAccount).build();
                    }
                    return Response.status(Response.Status.NOT_FOUND).entity("Account Not Found").build();
                }
                 return Response.status(Response.Status.NOT_FOUND).entity("Cannot find your user account. Please register with the system.").build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Only transfer transaction is allowed").build();
        }
    }
}
