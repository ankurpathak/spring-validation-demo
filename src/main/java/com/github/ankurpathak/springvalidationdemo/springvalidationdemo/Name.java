package com.github.ankurpathak.springvalidationdemo.springvalidationdemo;

import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Name {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public Name() {
    }
}
