package com.linkedu.it353.controller;

import com.linkedu.it353.model.UniversityProfile;
import com.linkedu.it353.model.UniversityProgram;
import com.linkedu.it353.model.UploadMaterials;
import com.linkedu.it353.model.User;
import com.linkedu.it353.service.UniversityProfileService;
import com.linkedu.it353.service.UniversityProgramService;
import com.linkedu.it353.service.UploadMaterialsService;
import com.linkedu.it353.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by sanket on 4/18/2017.
 */
@Controller
public class UniversityProfileController {
    @Autowired
    private UniversityProfileService universityProfileService;

    @Autowired
    private UserService userService;

    @Autowired
    private UniversityProgramService universityProgramService;

    @Autowired
    private UploadMaterialsService uploadMaterialsService;

    @RequestMapping(value = "/universities", method = RequestMethod.GET)
    public ModelAndView getAllUniversities() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("universities", universityProfileService.getAllUniversities());
        modelAndView.setViewName("university/profile/universities");
        return modelAndView;
    }
/*
    @RequestMapping("/university/{name}")
    public ModelAndView getUniversity(@PathVariable Integer name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("profile", universityProfileService.getUniversity(name));
        modelAndView.setViewName("university/profile/university");
        return modelAndView;
    }*/


    @RequestMapping(value = "/profileupdate", method = RequestMethod.GET)
    public ModelAndView universityProfileRegistration() {
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        UniversityProfile universityProfile = universityProfileService.getUniversity(user.getId());
        if (universityProfile == null)
            universityProfile = new UniversityProfile();
        modelAndView.addObject("universityProfile", universityProfile);
        modelAndView.setViewName("university/profile/profileupdate");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/university")
    public ModelAndView updateUniversity(@Valid UniversityProfile universityProfile, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();


        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("university/Profile/profileupdate");
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            universityProfile.setUser_id(user.getId());//needs to be updated
            universityProfileService.addUniversity(universityProfile);
            System.out.println("in update");
            modelAndView.setViewName("redirect:/recruiter/home");
//            modelAndView.setViewName("university/profile/home");
            //modelAndView.setViewName("redirect:/programupdate");
        }
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/university/search")
    public ModelAndView searchUniversity() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(new UniversityProfile());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);


        List<UploadMaterials> uploadMaterialsNew = uploadMaterialsService.findByUserId(user.getId());
        modelAndView.addObject("uploadMaterials", uploadMaterialsNew);

        boolean profileImage = false;
        for(UploadMaterials mat: uploadMaterialsNew){
            if(mat.getType().equals("Profile")){
                modelAndView.addObject("profileImage", "/image/"+mat.getFileName());
                profileImage = true;

            }

        }
        if (!profileImage) {
            modelAndView.addObject("profileImage", "/assets/img/avatar-dhg.png");
        }

        modelAndView.setViewName("university/profile/search");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/university/search")
    public ModelAndView searchUniversity(UniversityProfile universityProfile) {
        ModelAndView modelAndView = new ModelAndView();
        List<UniversityProfile> universityProfiles = universityProfileService.searchProfile(universityProfile);
        modelAndView.addObject("universityProfiles", universityProfiles);

        modelAndView.addObject("universityProfile", new UniversityProfile());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);

        List<UploadMaterials> uploadMaterialsNew = uploadMaterialsService.findByUserId(user.getId());
        modelAndView.addObject("uploadMaterials", uploadMaterialsNew);

        boolean profileImage = false;
        for(UploadMaterials mat: uploadMaterialsNew){
            if(mat.getType().equals("Profile")){
                modelAndView.addObject("profileImage", "/image/"+mat.getFileName());
                profileImage = true;

            }

        }
        if (!profileImage) {
            modelAndView.addObject("profileImage", "/assets/img/avatar-dhg.png");
        }

        modelAndView.setViewName("university/profile/searchresult");
        return modelAndView;
    }


    @RequestMapping(value = "/university/display", method = RequestMethod.POST)
    public ModelAndView getUniversity(@RequestParam("userId") Integer userId) {
        ModelAndView modelAndView = new ModelAndView();
        UniversityProfile universityProfile = universityProfileService.getUniversity(userId);
        modelAndView.addObject("universityProfile", universityProfile);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);

        List<UniversityProgram> universityProgram = universityProgramService.getAllPrograms(userId);
        modelAndView.addObject("universityProgram", universityProgram);

        List<UploadMaterials> uploadMaterialsNew = uploadMaterialsService.findByUserId(user.getId());
        modelAndView.addObject("uploadMaterials", uploadMaterialsNew);

        boolean profileImage = false;
        for(UploadMaterials mat: uploadMaterialsNew){
            if(mat.getType().equals("Profile")){
                modelAndView.addObject("profileImage", "/image/"+mat.getFileName());
                profileImage = true;

            }

        }
        if (!profileImage) {
            modelAndView.addObject("profileImage", "/assets/img/avatar-dhg.png");
        }

        modelAndView.setViewName("university/profile/display");
        return modelAndView;
    }


}

