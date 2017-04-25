package com.linkedu.it353.controller;


import com.linkedu.it353.model.UniversityProfile;
import com.linkedu.it353.model.User;
import com.linkedu.it353.service.UniversityProfileService;
import com.linkedu.it353.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by sanket on 4/18/2017.
 */
@Controller
public class UniversityProfileController {
    @Autowired
    private UniversityProfileService universityProfileService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/universities", method = RequestMethod.GET)
    public ModelAndView getAllUniversities() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("universities", universityProfileService.getAllUniversities());
        modelAndView.setViewName("university/profile/universities");
        return modelAndView;
    }

    @RequestMapping("/university/{name}")
    public ModelAndView getUniversity(@PathVariable Integer name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("profile", universityProfileService.getUniversity(name));
        modelAndView.setViewName("university/profile/university");
        return modelAndView;
    }


    @RequestMapping(value = "/profileupdate", method = RequestMethod.GET)
    public ModelAndView universityProfileRegistration() {
        ModelAndView modelAndView = new ModelAndView();
        UniversityProfile universityProfile = new UniversityProfile();
        modelAndView.addObject("profile", universityProfile);
        modelAndView.setViewName("university/universityProfile/profileupdate");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/university")
    public ModelAndView updateUniversity(@Valid UniversityProfile universityProfile, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        universityProfile.setUser_id(user.getId());//needs to be updated
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("university/universityProfile/profileupdate");
        } else {
            universityProfileService.addUniversity(universityProfile);
            System.out.println("in update");
//        modelAndView.setViewName("university/universityProfile/successful");
            modelAndView.setViewName("redirect:/programupdate");
        }
        return modelAndView;
    }
}