package com.linkedu.it353.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by sanket on 4/18/2017.
 */
@Entity
@Table(name = "university_profile")
public class UniversityProfile {
    @Id
    @NotNull
    private Integer user_id;
    private String university_name;
    private String city;
    private String state;
    private String address;
    private String website;
    private String terms;
    private Integer zip;
    private Integer contact;

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public Integer getContact() {
        return contact;
    }

    public void setContact(Integer contact) {
        this.contact = contact;
    }

    public UniversityProfile() {
    }

    public UniversityProfile(Integer user_id, String university_name, String city, String state, String address, String website, String terms, Integer zip, Integer contact) {
        this.user_id = user_id;
        this.university_name = university_name;
        this.city = city;
        this.state = state;
        this.address = address;
        this.website = website;
        this.terms = terms;
        this.zip = zip;
        this.contact = contact;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUniversity_name() {
        return university_name;
    }

    public void setUniversity_name(String university_name) {
        this.university_name = university_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }
}
