package com.github.ankurpathak.springvalidationdemo.springvalidationdemo;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/names")
public class NameResource  {


   private final LocalValidatorFactoryBean validator;
   private final MessageSource messageSource;

    public NameResource(LocalValidatorFactoryBean validator, MessageSource messageSource, MessageSource messageSource1) {
        this.validator = validator;
        this.messageSource = messageSource1;
    }


    @POST
    public Response create(@Valid Name name, @Context HttpServletRequest request){
        ResourceUtil.processValidation(name, validator, messageSource, request);
        return ResourceUtil.processSuccessNoContent();
    }




    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") String id){
        return Response.ok(new Name("Ankur", "Pathak", true)).build();
    }


}
