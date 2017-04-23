package com.linkedu.it353.service;

import com.linkedu.it353.model.StudentProfile;
import com.linkedu.it353.repository.StudentProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Kaiser on 4/21/2017.
 */
@Service("studentProfileService")
public class StudentProfileServiceImpl implements StudentProfileService {

    @Autowired
    StudentProfileRepository studentProfileRepository;

    @Override
    public StudentProfile findByUserId(int userId){
        return studentProfileRepository.findByUserId(userId);
    }

    @Override
    public void saveStudentProfile(StudentProfile studentProfile){
        studentProfileRepository.save(studentProfile);
    }
}
