package com.github.ankurpathak.springvalidationdemo.springvalidationdemo;

import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

public class ResourceUtil {

    public static void processValidation(Object target, LocalValidatorFactoryBean validator, MessageSource messageSource, HttpServletRequest request, ApiCode code, String message) {
        BindingResult result = new BindException(target, target.getClass().getSimpleName());
        validator.validate(target, result);
        if (result.hasErrors()) {
            throw new ValidationException(
                    result,
                    MessageUtil.getMessage(messageSource, message),
                    code
            );
        }
    }


    public static void processValidation(Object target, LocalValidatorFactoryBean validator, MessageSource messageSource, HttpServletRequest request) {
        processValidation(target, validator, messageSource, request, ApiCode.VALIDATION, ApiMessages.VALIDATION);
    }


    public static Response processSuccessNoContent(){
        return Response.noContent().build();
    }


}
