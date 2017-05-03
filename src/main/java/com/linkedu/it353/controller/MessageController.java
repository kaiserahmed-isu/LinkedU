package com.linkedu.it353.controller;


import com.linkedu.it353.mail.MailClient;
import com.linkedu.it353.model.*;
import com.linkedu.it353.service.BalanceService;
import com.linkedu.it353.service.MessageService;
import com.linkedu.it353.service.UploadMaterialsService;
import com.linkedu.it353.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * Created by Kaiser on 5/2/2017.
 */
@Controller
public class MessageController {
    @Autowired
    private UserService userService;

    @Autowired
    private UploadMaterialsService uploadMaterialsService;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MailClient mailClient;

    @RequestMapping(value = "/recruiter/message/{userId}", method = RequestMethod.GET)
    public ModelAndView messageStudent(@PathVariable("userId") int userId) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        User toUser = userService.findUserById(userId);

        modelAndView.addObject("user", user);
        modelAndView.addObject("toUser", toUser);
        modelAndView.addObject("message", new Message());


        //Profile picture
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

        modelAndView.setViewName("recruiter/message-student");

        return modelAndView;
    }

    @RequestMapping(value = "/recruiter/message", method = RequestMethod.POST)
    public ModelAndView messageStudentSubmit(Message message) {
        ModelAndView modelAndView = new ModelAndView();

        Date currentDate = new Date();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        User toUser = userService.findUserById(message.getToUserId());

        message.setFromUserId(user.getId());
        message.setCreatedDate(currentDate);
        message.setFromUserName(user.getFirstName()+ " " + user.getLastName());
        message.setStatus(0);

        messageService.SaveMessage(message);

        //Send email
        String recipient = toUser.getEmail();
        String subject = message.getSubject();
        String emailMessage = message.getText();

        mailClient.prepareAndSend(recipient, subject, emailMessage);

        Balance balance = balanceService.findBalance(user);
        int newBalanceAmount = balance.getBalanceAmount() - 1;

        balance.setBalanceAmount(newBalanceAmount);
        balanceService.saveBalance(balance);

        modelAndView.addObject("successMessage", "Message sent successfully");


        modelAndView.addObject("user", user);
        modelAndView.addObject("toUser", toUser);


        //Profile picture
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


        modelAndView.setViewName("recruiter/message-success");

        return modelAndView;
    }


}
