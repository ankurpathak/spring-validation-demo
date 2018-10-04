package com.github.ankurpathak.springvalidationdemo.springvalidationdemo;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Provider
@Component
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {


    private final MessageSource messageSource;

    public ValidationExceptionMapper(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Response toResponse(ValidationException e) {
        return ValidationUtil.handleValidationErrors(e, messageSource);
    }


}
