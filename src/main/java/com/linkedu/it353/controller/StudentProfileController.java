package com.linkedu.it353.controller;

import com.linkedu.it353.model.StudentActivity;
import com.linkedu.it353.model.StudentProfile;
import com.linkedu.it353.model.UploadMaterials;
import com.linkedu.it353.model.User;
import com.linkedu.it353.service.StudentActivityService;
import com.linkedu.it353.service.StudentProfileService;
import com.linkedu.it353.service.UploadMaterialsService;
import com.linkedu.it353.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Kaiser on 4/21/2017.
 */
@Controller
public class StudentProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private StudentProfileService studentProfileService;

    @Autowired
    private StudentActivityService studentActivityService;

    @Autowired
    private UploadMaterialsService uploadMaterialsService;

    @Value("${uploads.folder}")
    private String UPLOADED_FOLDER;
    //private static String UPLOADED_FOLDER = "E://temp//";

    @RequestMapping(value="/student/profile", method = RequestMethod.GET)
    public ModelAndView studentProfile(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        StudentProfile studentProfile = studentProfileService.findByUserId(user.getId());

        modelAndView.addObject("user", user);
        if (studentProfile != null) {
            modelAndView.addObject("studentProfile", studentProfile);
            return new ModelAndView("redirect:/student/profile/step2");
        }else {
            modelAndView.addObject("studentProfile", new StudentProfile());
        }
        modelAndView.setViewName("student/update-profile");
        return modelAndView;
    }

    @RequestMapping(value = "/student/profile", method = RequestMethod.POST)
    public ModelAndView studentProfileUpdate(@Valid StudentProfile studentProfile, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            modelAndView.addObject("user", user);
            modelAndView.setViewName("student/update-profile");
        } else {
            Date currentDate = new Date();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            studentProfile.setUserId(user.getId());
            studentProfile.setCreateDate(currentDate);
            studentProfileService.saveStudentProfile(studentProfile);
            //modelAndView.setViewName("student/update-profile");
            return new ModelAndView("redirect:/student/profile/step2");


        }
        return modelAndView;
    }

    @RequestMapping(value="/student/profile/step2", method = RequestMethod.GET)
    public ModelAndView studentProfileStep2(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        StudentProfile studentProfile = studentProfileService.findByUserId(user.getId());

        StudentActivity studentActivity = new StudentActivity();
        List<String> activityTypes = Arrays.asList("Activity", "Award", "Honor");
        List<StudentActivity> studentActivities = studentActivityService.findByUserIdAndActivityTypeIsIn(user.getId(), activityTypes);
        modelAndView.addObject("studentActivities", studentActivities);


        modelAndView.addObject("user", user);
        if (studentProfile != null) {
            modelAndView.addObject("studentProfile", studentProfile);
            modelAndView.addObject("studentActivity", studentActivity);
        }else {
            return new ModelAndView("redirect:/student/profile");
        }
        modelAndView.setViewName("student/profile-step2");
        return modelAndView;
    }

    @RequestMapping(value = "/student/profile/step2", method = RequestMethod.POST)
    public ModelAndView studentActivityUpdate(@Valid StudentActivity studentActivity, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            modelAndView.addObject("user", user);
            modelAndView.setViewName("student/profile-step2");
        } else {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            studentActivity.setUser(user);

            studentActivityService.saveStudentProfile(studentActivity);
            //modelAndView.setViewName("student/update-profile");
            modelAndView.addObject("successMessage", "Added Successfully!");
            return new ModelAndView("redirect:/student/profile/step2");


        }
        return modelAndView;
    }

    @RequestMapping(value="/student/profile/step3", method = RequestMethod.GET)
    public ModelAndView studentProfileStep3(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        StudentProfile studentProfile = studentProfileService.findByUserId(user.getId());

        StudentActivity studentActivity = new StudentActivity();
        List<String> activityTypes = Arrays.asList("Experience", "Community Service", "Extra-curricular");
        List<StudentActivity> studentActivities = studentActivityService.findByUserIdAndActivityTypeIsIn(user.getId(), activityTypes);
        modelAndView.addObject("studentActivities", studentActivities);


        modelAndView.addObject("user", user);
        if (studentProfile != null) {
            modelAndView.addObject("studentProfile", studentProfile);
            modelAndView.addObject("studentActivity", studentActivity);
        }else {
            return new ModelAndView("redirect:/student/profile");
        }
        modelAndView.setViewName("student/profile-step3");
        return modelAndView;
    }

    @RequestMapping(value = "/student/profile/step3", method = RequestMethod.POST)
    public ModelAndView studentActivityUpdateStep3(@Valid StudentActivity studentActivity, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            modelAndView.addObject("user", user);
            modelAndView.setViewName("student/profile-step2");
        } else {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            studentActivity.setUser(user);

            studentActivityService.saveStudentProfile(studentActivity);
            //modelAndView.setViewName("student/update-profile");
            modelAndView.addObject("successMessage", "Added Successfully!");
            return new ModelAndView("redirect:/student/profile/step3");


        }
        return modelAndView;
    }


    @RequestMapping(value="/student/profile/step4", method = RequestMethod.GET)
    public ModelAndView studentProfileStep4(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        StudentProfile studentProfile = studentProfileService.findByUserId(user.getId());

        modelAndView.addObject("user", user);
        if (studentProfile != null) {
            modelAndView.addObject("studentProfile", studentProfile);
        }else {
            return new ModelAndView("redirect:/student/profile");
        }
        modelAndView.setViewName("student/profile-step4");
        return modelAndView;
    }

    @RequestMapping(value="/student/profile/step4", method = RequestMethod.POST)
    public ModelAndView studentProfileUploadResume(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("type") String type){
        ModelAndView modelAndView = new ModelAndView();

        if (file.isEmpty()) {
            modelAndView.addObject("message", "Please select a file to upload");
            modelAndView.setViewName("upload");
            return modelAndView;
        }

        String fileExtentions = ".pdf,.doc,.docx";
        int lastIndex = file.getOriginalFilename().lastIndexOf('.');
        String substring = file.getOriginalFilename().substring(lastIndex, file.getOriginalFilename().length());

        if (!fileExtentions.contains(substring)){
            modelAndView.addObject("message", "Please upload only pdf, doc or docx files");
            modelAndView.setViewName("upload");
            return modelAndView;
        }

        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());

            UploadMaterials uploadMaterials = new UploadMaterials();
            uploadMaterials.setUserId(user.getId());
            uploadMaterials.setType(type);

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();


            String fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date())+ "_" +file.getOriginalFilename();

            uploadMaterials.setFileType(file.getContentType());
            uploadMaterials.setFileName(fileName);
            Path path = Paths.get(UPLOADED_FOLDER + fileName);
            Files.write(path, bytes);

            uploadMaterialsService.saveUploadMaterials(uploadMaterials);


            modelAndView.addObject("successMessage",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

            return new ModelAndView("redirect:/student/home");

        } catch (IOException e) {
            e.printStackTrace();
        }


        modelAndView.setViewName("student/profile-step4");
        return modelAndView;
    }

}
