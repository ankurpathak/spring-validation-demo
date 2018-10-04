package com.github.ankurpathak.springvalidationdemo.springvalidationdemo;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Optional;

@Provider
@Component
public class InvalidFormatExceptionMapper implements ExceptionMapper<InvalidFormatException> {

    private final MessageSource messageSource;

    public InvalidFormatExceptionMapper(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Response toResponse(InvalidFormatException e) {
        return Response
                .status(Response.Status.CONFLICT)
                .entity(
                        ApiResponse
                                .getInstance(
                                        ApiCode.VALIDATION,
                                        MessageUtil.getMessage(
                                                messageSource,
                                                ApiMessages.INVALID_FORMAT,
                                                e.getPath().stream().findFirst().map(JsonMappingException.Reference::getFieldName).orElse(""),
                                                String.valueOf(e.getValue())
                                        )

                                )
                )
                .build();
    }
}
