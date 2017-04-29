package com.linkedu.it353.model;

import javax.persistence.*;

/**
 * Created by sanket on 4/18/2017.
 */
@Entity
@Table(name = "university_program")
public class UniversityProgram {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer program_id;

    @Column(name = "userid")
    private Integer user;

    private String program_name;

    private Integer min_GPA, min_ACT;

    public UniversityProgram() {
    }

    public UniversityProgram(Integer program_id, Integer user, String program_name, Integer min_GPA, Integer min_ACT) {
        this.program_id = program_id;
        this.user = user;
        this.program_name = program_name;
        this.min_GPA = min_GPA;
        this.min_ACT = min_ACT;
    }

    public Integer getProgram_id() {
        return program_id;
    }

    public void setProgram_id(Integer program_id) {
        this.program_id = program_id;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public String getProgram_name() {
        return program_name;
    }

    public void setProgram_name(String program_name) {
        this.program_name = program_name;
    }

    public Integer getMin_GPA() {
        return min_GPA;
    }

    public void setMin_GPA(Integer min_GPA) {
        this.min_GPA = min_GPA;
    }

    public Integer getMin_ACT() {
        return min_ACT;
    }

    public void setMin_ACT(Integer min_ACT) {
        this.min_ACT = min_ACT;
    }
}
