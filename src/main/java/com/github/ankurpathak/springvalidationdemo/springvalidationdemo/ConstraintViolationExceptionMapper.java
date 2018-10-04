package com.github.ankurpathak.springvalidationdemo.springvalidationdemo;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Set;

@Provider
@Component
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private final LocalValidatorFactoryBean validator;
    private final MessageSource messageSource;

    public ConstraintViolationExceptionMapper(LocalValidatorFactoryBean validator, MessageSource messageSource) {
        this.validator = validator;
        this.messageSource = messageSource;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Response toResponse(ConstraintViolationException e) {
        BindException result = new BindException("","");
        ExtendeLocalValidatorFactoryBean extendedValidator = new ExtendeLocalValidatorFactoryBean(validator);
        extendedValidator.processConstraintViolations((Set)e.getConstraintViolations(), result);
        return ValidationUtil.handleValidationErrors(result,messageSource);
    }
}
