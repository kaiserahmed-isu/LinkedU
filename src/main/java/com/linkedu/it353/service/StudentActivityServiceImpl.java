package com.linkedu.it353.service;

import com.linkedu.it353.model.StudentActivity;
import com.linkedu.it353.repository.StudentActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Kaiser on 4/26/2017.
 */
@Service("studentActivityService")
public class StudentActivityServiceImpl implements StudentActivityService {

    @Autowired
    StudentActivityRepository studentActivityRepository;

    @Override
    public StudentActivity findByActivityId(int activityId) {
        return studentActivityRepository.findByActivityId(activityId);
    }

    @Override
    public List<StudentActivity> findByUserId(int userId){
        return studentActivityRepository.findByUserId(userId);
    }

    public List<StudentActivity> findByUserIdAndActivityTypeIsIn(int userId, List<String> activityTypes){
        return studentActivityRepository.findByUserIdAndActivityTypeIsIn(userId, activityTypes);
    }

    @Override
    public void saveStudentProfile(StudentActivity studentActivity) {
        studentActivityRepository.save(studentActivity);
    }
}
