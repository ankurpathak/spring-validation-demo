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
        return handleValidationErrors(e);
    }



    private ValidationErrorDto processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDto validationErrorDto = new ValidationErrorDto();
        for (FieldError fieldError : fieldErrors) {
            String localizedErrorMessage = resolveLocalizedFieldErrorMessage(fieldError);
            validationErrorDto.addError(fieldError.getField(), localizedErrorMessage);
        }
        return validationErrorDto;
    }


    private String resolveLocalizedFieldErrorMessage(FieldError fieldError) {
        String localizedErrorMessage = MessageUtil.getMessage(messageSource, fieldError);
        //If the message was not found, return the most accurate field error code instead.
        //You can remove this check if you prefer to get the default error message.
        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            localizedErrorMessage = fieldErrorCodes[0];
        }


        return localizedErrorMessage;
    }

    private List<String> processGlobalErrors(List<ObjectError> objectErrors) {
        List<String> errors = new ArrayList<>();
        for (ObjectError objectError : objectErrors) {
            String localizedErrorMessage = resolveLocalizedObjectErrorMessage(objectError);
            errors.add(localizedErrorMessage);
        }
        return errors;
    }

    private String resolveLocalizedObjectErrorMessage(ObjectError objectError) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(objectError, currentLocale);

        //If the message was not found, return the most accurate field error code instead.
        //You can remove this check if you prefer to get the default error message.
        if (localizedErrorMessage.equals(objectError.getDefaultMessage())) {
            String[] objectErrorCodes = objectError.getCodes();
            localizedErrorMessage = objectErrorCodes[0];
        }

        return localizedErrorMessage;
    }


    private Response handleValidationErrors(Exception ex) {
        String message = null;
        ApiCode code = null;
        BindingResult result = null;
        if (ex instanceof BindException) {
            result = ((BindException) ex).getBindingResult();
        } else if (ex instanceof MethodArgumentNotValidException) {
            result = ((MethodArgumentNotValidException) ex).getBindingResult();
        } else if (ex instanceof ValidationException) {
            ValidationException vEx = (ValidationException) ex;
            result = vEx.getBindingResult();
            message = vEx.getMessage();
            code = vEx.getCode();
        }
        if(message == null)
            message = MessageUtil.getMessage(messageSource, ApiMessages.VALIDATION);
        if(code == null)
            code = ApiCode.VALIDATION;

        ApiResponse dto = ApiResponse.getInstance(code, message);
        if (result != null) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            ValidationErrorDto validationErrorDto = processFieldErrors(fieldErrors);
            List<ObjectError> objectErrors = result.getGlobalErrors();
            List<String> starErrors = processGlobalErrors(objectErrors);
            for (String starError : starErrors) {
                validationErrorDto.addError("*", starError);
            }
            dto.addExtra("hints", validationErrorDto);
        }
        return Response.status(Response.Status.CONFLICT).entity(dto).build();
    }


}
