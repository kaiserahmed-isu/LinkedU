package com.linkedu.it353.model;

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

	@Column(name = "actScore")
	private int actScore;

	private float cgpa;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	private Date createDate;

	private String ged;

	@Column(name="graduationGedDate")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date graduationGedDate;

	@Column(name = "hsName")
	@NotEmpty(message = "*Please provide your high school name")
	private String hsName;

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

	public int getActScore() {
		return this.actScore;
	}

	public void setActScore(int actScore) {
		this.actScore = actScore;
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

	public Date getGraduationGedDate() {
		return this.graduationGedDate;
	}

	public void setGraduationGedDate(Date graduationGedDate) {
		this.graduationGedDate = graduationGedDate;
	}

	public String getHsName() {
		return this.hsName;
	}

	public void setHsName(String hsName) {
		this.hsName = hsName;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}