package com.linkedu.it353.service;

/**
 * Created by Kaiser Ahmed on 3/8/2017.
 */
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.linkedu.it353.model.Role;
import com.linkedu.it353.model.User;
import com.linkedu.it353.repository.RoleRepository;
import com.linkedu.it353.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        user.setActive(1);
//        //Role userRole = roleRepository.findByRole("ADMIN");
//        Role userRole = roleRepository.findByRole("FREE_USER");
//        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public void saveStudentUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
//        Role userRole = roleRepository.findByRole("ADMIN");
        Role userRole = roleRepository.findByRole("STUDENT");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }
    
    @Override
    public void saveRecruiterUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("RECRUITER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        user.setPassword(user.getPassword());
        userRepository.save(user);
    }

    @Override
    public void updateToPaidUser(User user) {
        Role userRole = roleRepository.findByRole("PAID_USER");
        User oldUser = userRepository.findById(user.getId());
        oldUser.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(oldUser);
    }

    @Override
    public User findUserByValidateCode(String validateCode) {
        return userRepository.findByValidateCode(validateCode);
    }

    @Override
    public Integer countDistinctByRoles(Role role){
        return userRepository.countDistinctByRoles(role);
    }

    @Override
    public List<User> searchStudents(float cgpa, int act, String HS_name) {
        return userRepository.findDistinctByStudentProfile_CgpaGreaterThanEqualAndStudentProfile_ActScoreGreaterThanEqualOrStudentProfile_HsNameLike(cgpa, act, HS_name);
    }

    @Override
    public List<User> findAllByRole(Role role) {
        return userRepository.findAllByRoles(role);
    }

    @Override
    public User findUserById(int userId) {
        return userRepository.findById(userId);
    }
}