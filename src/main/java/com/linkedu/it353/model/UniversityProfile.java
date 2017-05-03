package com.linkedu.it353.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by sanket on 4/18/2017.
 */

@Entity
@Table(name = "university_profile")
public class UniversityProfile {
    @Id
    private Integer user_id;

    @Column(name = "university_name")
    private String university_name;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "address")
    private String address;

    @Column(name = "website")
    private String website;

    @Column(name = "terms")
    private String terms;

    @Column(name = "zip")
    private Integer zip;

    @Column(name = "contact")
    private String contact;

    public UniversityProfile() {
    }

    public UniversityProfile(Integer user_id, String university_name, String city, String state, String address, String website, String terms, Integer zip, String contact, List<UniversityProgram> universityProgramSet) {
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

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}