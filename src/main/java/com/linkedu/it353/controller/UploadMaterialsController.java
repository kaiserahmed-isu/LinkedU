package com.linkedu.it353.controller;

import com.linkedu.it353.model.UploadMaterials;
import com.linkedu.it353.model.User;
import com.linkedu.it353.service.UploadMaterialsService;
import com.linkedu.it353.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kaiser on 4/21/2017.
 */
@Controller
public class UploadMaterialsController {
    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "E://temp//";

    @Autowired
    private UserService userService;

    @Autowired
    private UploadMaterialsService uploadMaterialsService;

    @RequestMapping(value="/upload", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("upload");
        return modelAndView;
    }

    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public ModelAndView singleFileUpload(@RequestParam("file") MultipartFile file,
                                         @RequestParam("type") String type) {
        ModelAndView modelAndView = new ModelAndView();

        String fileExtentions = ".jpeg,.png,.mp4,.jpg,.gif,.pdf,.doc,.docx";
        int lastIndex = file.getOriginalFilename().lastIndexOf('.');
        String substring = file.getOriginalFilename().substring(lastIndex, file.getOriginalFilename().length());

        if (!fileExtentions.contains(substring)){
            modelAndView.addObject("messageError", "Please upload only jpeg, png, mp4, jpg, gif, .pdf, .doc, .docx files");
            modelAndView.setViewName("student/home");
            //return modelAndView;
            return new ModelAndView("redirect:/student/home");
        }
        if (file.isEmpty()) {
            modelAndView.addObject("messageError", "Please select a file to upload");
            modelAndView.setViewName("student/home");
            //return modelAndView;
            return new ModelAndView("redirect:/student/home");
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

        } catch (IOException e) {
            e.printStackTrace();
        }
        //modelAndView.setViewName("student/home");
        return new ModelAndView("redirect:/student/home");
    }


}
