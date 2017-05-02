package com.linkedu.it353.controller;

/**
 * Created by Kaiser Ahmed on 3/8/2017.
 */

import javax.validation.Valid;

import com.linkedu.it353.model.*;
import com.linkedu.it353.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentProfileService studentProfileService;

    @Autowired
    private UniversityProfileService universityProfileService;

    @Autowired
    private UniversityProgramService universityProgramService;

    @Autowired
    private StudentActivityService studentActivityService;

    @Autowired
    private UploadMaterialsService uploadMaterialsService;

//    private static String UPLOADED_FOLDER = "E://temp//";

    @Value("${uploads.folder}")
    private String UPLOADED_FOLDER;

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @RequestMapping(value = "/recruiter/home", method = RequestMethod.GET)
    public ModelAndView homeMember() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        UniversityProfile universityProfile = universityProfileService.getUniversity(user.getId());
        List<UniversityProgram> universityProgram = universityProgramService.getAllPrograms(user.getId());
        modelAndView.addObject("user", user);
        if (universityProfile != null) {
            modelAndView.addObject("program", new UniversityProgram());
            modelAndView.addObject("universityProfile", universityProfile);
            modelAndView.addObject("universityProgram", universityProgram);
            modelAndView.setViewName("university/profile/home");
        }
        else {
            return new ModelAndView("redirect:/profileupdate");
        }

/*
        modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("memberMessage","Content Available Only for Users with Recruiter Member Role");
        modelAndView.setViewName("member/home");
*/
        return modelAndView;
    }

    @RequestMapping(value = "/student/home", method = RequestMethod.GET)
    public ModelAndView homestudent() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        StudentProfile studentProfile = studentProfileService.findByUserId(user.getId());

        modelAndView.addObject("user", user);
        if (studentProfile != null) {
            modelAndView.addObject("studentProfile", studentProfile);

            List<String> activityTypes1 = Arrays.asList("Activity", "Award", "Honor");
            List<StudentActivity> studentActivities1 = studentActivityService.findByUserIdAndActivityTypeIsIn(user.getId(), activityTypes1);
            modelAndView.addObject("studentActivities1", studentActivities1);

            List<String> activityTypes2 = Arrays.asList("Experience", "Community Service", "Extra-curricular");
            List<StudentActivity> studentActivityTypes2 = studentActivityService.findByUserIdAndActivityTypeIsIn(user.getId(), activityTypes2);
            modelAndView.addObject("studentActivities2", studentActivityTypes2);

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


            modelAndView.setViewName("student/home");
        } else {
            return new ModelAndView("redirect:/student/profile");
        }

        return modelAndView;
    }


    @RequestMapping(value = "/student/home", method = RequestMethod.POST)
    public ModelAndView singleFileUpload(@RequestParam("file") MultipartFile file,
                                         @RequestParam("type") String type) {
        ModelAndView modelAndView = new ModelAndView();


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());


        if (file.isEmpty()) {
            modelAndView.addObject("messageError", "Please select a file to upload");
            modelAndView.setViewName("student/home");
            return modelAndView;
        }

        String fileExtentions = ".jpeg,.png,.mp4,.jpg,.gif,.pdf,.doc,.docx";
        int lastIndex = file.getOriginalFilename().lastIndexOf('.');
        String substring = file.getOriginalFilename().substring(lastIndex, file.getOriginalFilename().length());

        if (!fileExtentions.contains(substring)) {
            modelAndView.addObject("messageError", "Please upload only jpeg, png, mp4, jpg, gif, .pdf, .doc, .docx files");
            modelAndView.setViewName("student/home");
            return modelAndView;

        }
        try {

            UploadMaterials uploadMaterials = new UploadMaterials();
            uploadMaterials.setUserId(user.getId());
            uploadMaterials.setType(type);

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();

            String fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + "_" + file.getOriginalFilename();

            uploadMaterials.setFileType(file.getContentType());
            uploadMaterials.setFileName(fileName);
            Path path = Paths.get(UPLOADED_FOLDER + fileName);
            Files.write(path, bytes);

            uploadMaterialsService.saveUploadMaterials(uploadMaterials);

            modelAndView.addObject("successMessage",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

            StudentProfile studentProfile = studentProfileService.findByUserId(user.getId());

            modelAndView.addObject("user", user);

            if (studentProfile != null) {
                modelAndView.addObject("studentProfile", studentProfile);

                List<String> activityTypes1 = Arrays.asList("Activity", "Award", "Honor");
                List<StudentActivity> studentActivities1 = studentActivityService.findByUserIdAndActivityTypeIsIn(user.getId(), activityTypes1);
                modelAndView.addObject("studentActivities1", studentActivities1);

                List<String> activityTypes2 = Arrays.asList("Experience", "Community Service", "Extra-curricular");
                List<StudentActivity> studentActivityTypes2 = studentActivityService.findByUserIdAndActivityTypeIsIn(user.getId(), activityTypes2);
                modelAndView.addObject("studentActivities2", studentActivityTypes2);

                List<UploadMaterials> uploadMaterialsNew = uploadMaterialsService.findByUserId(user.getId());
                modelAndView.addObject("uploadMaterials", uploadMaterialsNew);

                modelAndView.setViewName("student/home");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        modelAndView.setViewName("student/home");
        return modelAndView;
    }

    @GetMapping("/file/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> showFile(@PathVariable String filename) throws IOException {
        Path file = Paths.get(UPLOADED_FOLDER + filename);

        Resource resource = new UrlResource(file.toUri());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"")
                .body(resource);
    }

    @GetMapping("/image/{filename:.+}")
    public HttpEntity<byte[]> showPostsImage(@PathVariable String filename) throws IOException {
        Path file = Paths.get(UPLOADED_FOLDER + filename);
        byte[] image = IOUtils.toByteArray(FileUtils.openInputStream(new File(file.toString())));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(image.length);

        return new HttpEntity<>(image, headers);
    }



    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public ModelAndView defaultAfterLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString();

        String targetUrl = "";
        if (role.contains("ADMIN")) {
            targetUrl = "redirect:/admin/home";
        } else if (role.contains("STUDENT")) {
            targetUrl = "redirect:/student/home";

        } else if (role.contains("RECRUITER")) {
            targetUrl = "redirect:/recruiter/home";
        } else {
            targetUrl = "redirect:/access-denied";
        }
        return new ModelAndView(targetUrl);
    }
}