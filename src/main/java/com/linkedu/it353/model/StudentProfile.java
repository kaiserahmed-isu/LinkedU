package com.linkedu.it353.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the student_profile database table.
 * 
 */
@Entity
@Table(name="student_profile")
public class StudentProfile {

	@Id
	@Column(name="user_id")
	private int userId;

	private int ACT_score;

	private float cgpa;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	private String ged;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date graduation_GED_date;

	@Column(name = "HS_name")
	@NotEmpty(message = "*Please provide your high school name")
	private String HS_name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_date")
	private Date updateDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


	public StudentProfile() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getACT_score() {
		return this.ACT_score;
	}

	public void setACT_score(int ACT_score) {
		this.ACT_score = ACT_score;
	}

	public float getCgpa() {
		return this.cgpa;
	}

	public void setCgpa(float cgpa) {
		this.cgpa = cgpa;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getGed() {
		return this.ged;
	}

	public void setGed(String ged) {
		this.ged = ged;
	}

	public Date getGraduation_GED_date() {
		return this.graduation_GED_date;
	}

	public void setGraduation_GED_date(Date graduation_GED_date) {
		this.graduation_GED_date = graduation_GED_date;
	}

	public String getHS_name() {
		return this.HS_name;
	}

	public void setHS_name(String HS_name) {
		this.HS_name = HS_name;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}