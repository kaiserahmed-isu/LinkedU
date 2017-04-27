package com.linkedu.it353.service;

import com.linkedu.it353.model.StudentActivity;

import java.util.List;

/**
 * Created by Kaiser on 4/26/2017.
 */
public interface StudentActivityService {
    public StudentActivity findByActivityId(int activityId);
    public List<StudentActivity> findByUserId(int userId);
    public List<StudentActivity> findByUserIdAndActivityTypeIsIn(int userId, List<String> activityTypes);
    public void saveStudentProfile(StudentActivity studentActivity);
}
