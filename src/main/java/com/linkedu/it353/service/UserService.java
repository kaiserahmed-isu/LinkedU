package com.linkedu.it353.service;

/**
 * Created by Kaiser Ahmed on 3/8/2017.
 */
import com.linkedu.it353.model.Role;
import com.linkedu.it353.model.User;

import java.util.List;

public interface UserService {
    public User findUserByEmail(String email);
    public void saveUser(User user);
    public void saveStudentUser(User user);
    public void saveRecruiterUser(User user);
    public void updateToPaidUser(User user);
    public User findUserByValidateCode(String validateCode);
    public void updateUser(User user);
    public Integer countDistinctByRoles(Role role);
    public List<User> searchStudents(float cgpa, int act, String HS_name);
    public List<User> findAllByRole(Role role);
    public User findUserById(int userId);
}
