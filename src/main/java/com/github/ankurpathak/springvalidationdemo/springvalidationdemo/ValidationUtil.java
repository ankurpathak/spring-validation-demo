package com.github.ankurpathak.springvalidationdemo.springvalidationdemo;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ValidationUtil {
    public static ValidationErrorDto processFieldErrors(List<FieldError> fieldErrors, MessageSource messageSource) {
        ValidationErrorDto validationErrorDto = new ValidationErrorDto();
        for (FieldError fieldError : fieldErrors) {
            String localizedErrorMessage = resolveLocalizedFieldErrorMessage(fieldError, messageSource);
            validationErrorDto.addError(fieldError.getField(), localizedErrorMessage);
        }
        return validationErrorDto;
    }


    public static String resolveLocalizedFieldErrorMessage(FieldError fieldError, MessageSource messageSource) {
        String localizedErrorMessage = MessageUtil.getMessage(messageSource, fieldError);
        //If the message was not found, return the most accurate field error code instead.
        //You can remove this check if you prefer to get the default error message.
        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            if (!ArrayUtils.isEmpty(fieldErrorCodes))
                    localizedErrorMessage = fieldErrorCodes[0];
        }
        return localizedErrorMessage;
    }

    public static List<String> processGlobalErrors(List<ObjectError> objectErrors, MessageSource messageSource) {
        List<String> errors = new ArrayList<>();
        for (ObjectError objectError : objectErrors) {
            String localizedErrorMessage = resolveLocalizedObjectErrorMessage(objectError, messageSource);
            errors.add(localizedErrorMessage);
        }
        return errors;
    }

    public static String resolveLocalizedObjectErrorMessage(ObjectError objectError, MessageSource messageSource) {
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


    public static Response handleValidationErrors(Exception ex, MessageSource messageSource) {
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
        if (message == null)
            message = MessageUtil.getMessage(messageSource, ApiMessages.VALIDATION);
        if (code == null)
            code = ApiCode.VALIDATION;

        ApiResponse dto = ApiResponse.getInstance(code, message);
        if (result != null) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            ValidationErrorDto validationErrorDto = processFieldErrors(fieldErrors, messageSource);
            List<ObjectError> objectErrors = result.getGlobalErrors();
            List<String> starErrors = processGlobalErrors(objectErrors, messageSource);
            for (String starError : starErrors) {
                validationErrorDto.addError("*", starError);
            }
            dto.addExtra("hints", validationErrorDto);
        }
        return Response.status(Response.Status.CONFLICT).entity(dto).build();
    }

}
