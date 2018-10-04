package com.github.ankurpathak.springvalidationdemo.springvalidationdemo;

import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ExtendeLocalValidatorFactoryBean extends LocalValidatorFactoryBean {


    private final LocalValidatorFactoryBean localValidatorFactoryBean;

    public ExtendeLocalValidatorFactoryBean(LocalValidatorFactoryBean localValidatorFactoryBean) {
        this.localValidatorFactoryBean = localValidatorFactoryBean;
    }


    @Override
    public void processConstraintViolations(Set<ConstraintViolation<Object>> violations, Errors errors) {
        super.processConstraintViolations(violations, errors);
    }
}
