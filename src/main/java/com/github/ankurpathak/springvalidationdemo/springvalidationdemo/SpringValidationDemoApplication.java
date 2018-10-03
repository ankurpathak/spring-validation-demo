package com.github.ankurpathak.springvalidationdemo.springvalidationdemo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResultUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@SpringBootApplication
public class SpringValidationDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringValidationDemoApplication.class, args);
    }
}

@Component
class Main implements ApplicationRunner{



    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}