package com.linkedu.it353.repository;

import com.linkedu.it353.model.UniversityProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sanket on 4/18/2017.
 */
@Repository("UniversityProgramRepository")
public interface UniversityProgramRepository extends JpaRepository<UniversityProgram,Integer> {
    List<UniversityProgram> findByUser(Integer user);

}
