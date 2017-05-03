package com.linkedu.it353.controller;

import com.linkedu.it353.model.*;
import com.linkedu.it353.repository.BalanceRepository;
import com.linkedu.it353.repository.RoleRepository;
import com.linkedu.it353.service.BalanceService;
import com.linkedu.it353.service.StudentProfileService;
import com.linkedu.it353.service.UploadMaterialsService;
import com.linkedu.it353.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Kaiser on 5/1/2017.
 */
@Controller
public class StudentSearchController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentProfileService studentProfileService;

    @Autowired
    private UploadMaterialsService uploadMaterialsService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BalanceService balanceService;

    @RequestMapping(value = "/recruiter/search-student", method = RequestMethod.GET)
    public ModelAndView searchStudent() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        modelAndView.addObject("user", user);

        List<UploadMaterials> uploadMaterials = uploadMaterialsService.findByUserId(user.getId());
        modelAndView.addObject("uploadMaterials", uploadMaterials);

        boolean profileImage = false;
        for(UploadMaterials mat: uploadMaterials){
            if(mat.getType().equals("Profile")){
                modelAndView.addObject("profileImage", "/image/"+mat.getFileName());
                profileImage = true;

            }

        }
        if (!profileImage) {
            modelAndView.addObject("profileImage", "/assets/img/avatar-dhg.png");
        }

        Role studentRole = roleRepository.findByRole("STUDENT");
        List<User> students = userService.findAllByRole(studentRole);

        Balance balance = balanceService.findBalance(user);

        modelAndView.addObject("balance", balance);


        modelAndView.addObject("students", students);
        modelAndView.setViewName("university/search-student");

        return modelAndView;
    }

    @RequestMapping(value = "/recruiter/search-student", method = RequestMethod.POST)
    public ModelAndView searchStudentPost(@RequestParam("cgpa") String cgpa, @RequestParam("act") String act) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        modelAndView.addObject("user", user);

        List<UploadMaterials> uploadMaterials = uploadMaterialsService.findByUserId(user.getId());
        modelAndView.addObject("uploadMaterials", uploadMaterials);

        boolean profileImage = false;
        for(UploadMaterials mat: uploadMaterials){
            if(mat.getType().equals("Profile")){
                modelAndView.addObject("profileImage", "/image/"+mat.getFileName());
                profileImage = true;

            }

        }
        if (!profileImage) {
            modelAndView.addObject("profileImage", "/assets/img/avatar-dhg.png");
        }

        Balance balance = balanceService.findBalance(user);

        modelAndView.addObject("balance", balance);

//        Role studentRole = roleRepository.findByRole("STUDENT");
        List<User> students = userService.searchStudents(Float.parseFloat(cgpa), Integer.parseInt(act), "");

        modelAndView.addObject("students", students);
        modelAndView.setViewName("university/search-student");

        return modelAndView;
    }

}
