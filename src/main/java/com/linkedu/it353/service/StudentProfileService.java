package com.linkedu.it353.service;

import com.linkedu.it353.model.StudentProfile;

/**
 * Created by Kaiser on 4/21/2017.
 */
public interface StudentProfileService {
    public StudentProfile findByUserId(int userId);
    public void saveStudentProfile(StudentProfile studentProfile);

}
