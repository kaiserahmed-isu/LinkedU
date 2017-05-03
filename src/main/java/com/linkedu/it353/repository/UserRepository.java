package com.linkedu.it353.repository;

/**
 * Created by Kaiser Ahmed on 3/8/2017.
 */
import com.linkedu.it353.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linkedu.it353.model.User;

import java.util.List;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findById(int id);
    User findByValidateCode(String validateCode);

    Integer countDistinctByRoles(Role role);

    List<User> findAllByRoles(Role role);

    List<User> findDistinctByStudentProfile_CgpaGreaterThanEqualAndStudentProfile_ActScoreGreaterThanEqualOrStudentProfile_HsNameLike(float cgpa, int act, String hsName);

}
