package com.example.contacts;

import java.io.Serializable;

public class Contact{
    int id;
    String name, number, email, organisation;

    public Contact(int id, String name, String number, String email, String organisation) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.organisation = organisation;
    }

    public Contact(String name, String number, String email, String organisation) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.organisation = organisation;
    }

    public Contact() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }
}
