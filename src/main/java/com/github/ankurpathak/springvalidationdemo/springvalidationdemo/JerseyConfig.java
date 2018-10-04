package com.github.ankurpathak.springvalidationdemo.springvalidationdemo;


import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        this
                .register(NameResource.class)
                .register(ValidationExceptionMapper.class)
                .register(ConstraintViolationExceptionMapper.class)
                .register(InvalidFormatExceptionMapper.class)
                .property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);

    }

}
