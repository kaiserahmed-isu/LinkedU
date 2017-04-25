package com.linkedu.it353.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by sanket on 4/21/2017.
 */
@Entity
@Table(name = "showcase")
public class Showcase {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer showcase_id;
    private Integer university_id;
    private Integer priority;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date expire_date;

    public Showcase() {
    }

    public Showcase(Integer university_id, Integer priority, Date expire_date) {
        this.university_id = university_id;
        this.priority = priority;
        this.expire_date = expire_date;
    }

    public Integer getShowcase_id() {
        return showcase_id;
    }

    public void setShowcase_id(Integer showcase_id) {
        this.showcase_id = showcase_id;
    }

    public Integer getUniversity_id() {
        return university_id;
    }

    public void setUniversity_id(Integer university_id) {
        this.university_id = university_id;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(Date expire_date) {
        this.expire_date = expire_date;
    }

    @Override
    public String toString() {
        return "Showcase{" +
                "showcase_id=" + showcase_id +
                ", university_id=" + university_id +
                ", priority=" + priority +
                ", expire_date=" + expire_date +
                '}';
    }
}
