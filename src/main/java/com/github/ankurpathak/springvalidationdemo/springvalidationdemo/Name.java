package com.github.ankurpathak.springvalidationdemo.springvalidationdemo;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlRootElement;


//@XmlRootElement
public class Name {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @AssertTrue
    boolean flag;


    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

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


    public Name(String firstName, String lastName, boolean flag) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.flag = flag;
    }


    public Name() {
    }
}
