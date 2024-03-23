package com.inventorymanagementservice.models.UserModels;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User {
    String id;

    String firstName;

    String lastName;

    String emailAddress;

    Date dateOfBirth;

    ShippingDetails address;

    Map<String, String> attributes; // user device info, user device os,

    public User() {
        attributes = new HashMap<>();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public ShippingDetails getAddress() {
        return address;
    }

    public void setAddress(ShippingDetails address) {
        this.address = address;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
