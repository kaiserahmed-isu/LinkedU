package com.linkedu.it353.repository;

import com.linkedu.it353.model.StudentActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kaiser on 4/26/2017.
 */
@Repository("StudentActivityRepository")
public interface StudentActivityRepository extends JpaRepository<StudentActivity, Long> {
    StudentActivity findByActivityId(int activityId);
    List<StudentActivity> findByUserId(int userId);
    List<StudentActivity> findByUserIdAndActivityTypeIsIn(int userId, List<String> activityTypes);
}
