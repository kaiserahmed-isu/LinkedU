package com.linkedu.it353.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created by Kaiser on 4/26/2017.
 */
@Entity
@Table(name = "student_activity")
public class StudentActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="activity_id")
    private int activityId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "activity_type")
    @NotEmpty(message = "*Please provide activity type")
    private String activityType;

    @Column(name = "activity_title ")
    private String activityTitle ;

    @Column(name = "activity_details ")
    private String  activityDetails ;

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getActivityDetails() {
        return activityDetails;
    }

    public void setActivityDetails(String activityDetails) {
        this.activityDetails = activityDetails;
    }
}
