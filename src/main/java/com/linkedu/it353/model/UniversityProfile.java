package com.linkedu.it353.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by sanket on 4/18/2017.
 */

@Entity
@Table(name = "university_profile")
public class UniversityProfile {
    @Id
    private Integer user_id;

    @Column(name = "university_name")
    private String universityName;

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

    @OneToMany(targetEntity = UniversityProgram.class, cascade = CascadeType.ALL, mappedBy = "universityProfile", fetch=FetchType.EAGER)
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "universityProfile", fetch=FetchType.EAGER)
    private List universityProgramSet;

    public UniversityProfile() {
    }

    public UniversityProfile(Integer user_id, String universityName, String city, String state, String address, String website, String terms, Integer zip, String contact, List<UniversityProgram> universityProgramSet) {
        this.user_id = user_id;
        this.universityName = universityName;
        this.city = city;
        this.state = state;
        this.address = address;
        this.website = website;
        this.terms = terms;
        this.zip = zip;
        this.contact = contact;
        this.universityProgramSet = universityProgramSet;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
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

    public List<UniversityProgram> getUniversityProgramSet() {
        return universityProgramSet;
    }

    public void setUniversityProgramSet(List<UniversityProgram> universityProgramSet) {
        this.universityProgramSet = universityProgramSet;
    }
}