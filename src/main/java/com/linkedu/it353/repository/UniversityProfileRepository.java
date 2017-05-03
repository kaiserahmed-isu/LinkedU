package com.linkedu.it353.repository;


import com.linkedu.it353.model.UniversityProfile;
import com.linkedu.it353.model.UniversityProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sanket on 4/18/2017.
 */
@Repository("university_profile")
public interface UniversityProfileRepository extends JpaRepository<UniversityProfile, Integer> {
    @Query("select u from UniversityProfile u where u.university_name LIKE %?1% or u.city LIKE %?2% or u.city LIKE %?3% or u.city LIKE %?4%")
    List<UniversityProfile> findByUniversityNameAndCityAndStateAndZipIgnoreCase(String university_name, String city, String state, Integer zip);
}
