/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankingapplication;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam; 
import javax.ws.rs.core.MediaType;

/**
 *
 * @author hassan
 */
@Path("/test")
public class Test {
    
    @GET
    @Path("/{id}")
    public String test(@PathParam("id") int id){
        System.out.println("was it called");
        return "Hello World " + id;
    }
    
    @GET
    public String test(){
        return "Hello World";
    }
}
