package com.linkedu.it353.repository;

import com.linkedu.it353.model.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Kaiser on 4/21/2017.
 */
@Repository("StudentProfileRepository")
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    StudentProfile findByUserId(int userId);
    }
