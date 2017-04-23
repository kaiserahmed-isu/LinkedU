package com.linkedu.it353.controller;

import com.linkedu.it353.model.StudentProfile;
import com.linkedu.it353.model.User;
import com.linkedu.it353.service.StudentProfileService;
import com.linkedu.it353.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;

/**
 * Created by Kaiser on 4/21/2017.
 */
@Controller
public class StudentProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private StudentProfileService studentProfileService;

    @RequestMapping(value="/student/profile", method = RequestMethod.GET)
    public ModelAndView studentProfile(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        StudentProfile studentProfile = studentProfileService.findByUserId(user.getId());

        modelAndView.addObject("user", user);
        if (studentProfile != null) {
            modelAndView.addObject("studentProfile", studentProfile);
        }else {
            modelAndView.addObject("studentProfile", new StudentProfile());
        }
        modelAndView.setViewName("student/profile");
        return modelAndView;
    }

    @RequestMapping(value = "/student/profile", method = RequestMethod.POST)
    public ModelAndView studentProfileUpdate(@Valid StudentProfile studentProfile, BindingResult bindingResult) {

        Date currentDate = new Date();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            studentProfile.setUserId(user.getId());
            studentProfile.setCreateDate(currentDate);
            studentProfileService.saveStudentProfile(studentProfile);

        return new ModelAndView("redirect:/student/profile");
    }

}
