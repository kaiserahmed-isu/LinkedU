package com.linkedu.it353.controller;

/**
 * Created by Kaiser on 4/19/2017.
 */

import com.linkedu.it353.mail.MailClient;
import com.linkedu.it353.model.User;
import com.linkedu.it353.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class RecruiterRegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailClient mailClient;

    @RequestMapping(value="/registration-recruiter", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration-recruiter");
        return modelAndView;
    }

    @RequestMapping(value = "/registration-recruiter", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration-recruiter");
        } else {
            String uuid = UUID.randomUUID().toString();
            user.setValidateCode(uuid);
            user.setEmailValid(0);
            user.setHasProfile(0);
            userService.saveRecruiterUser(user);

            //given
            String recipient = user.getEmail();
            String subject = "Verify your email - LinkedU";
            String message = "Thank you for registering as a recruiter. Please click here http://localhost:8080/verifyRecruiter/"+uuid+" to verify your account. Thank you! - LinkedU";
            //when
            mailClient.prepareAndSend(recipient, subject, message);

            modelAndView.addObject("successMessage", "Your account created successfully. Now please check your email to verify your account");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration-recruiter");

        }
        return modelAndView;
    }


    @RequestMapping(value = "/verifyRecruiter/{code}", method = RequestMethod.GET)
    public ModelAndView verifyStudent(@PathVariable("code") String code) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByValidateCode(code);
        if (user == null)  {
            modelAndView.addObject("successMessage", "Sorry, code is not valid.");
            modelAndView.setViewName("login");
        } else {
            String uuid = null;
            user.setValidateCode(uuid);
            user.setEmailValid(1);
            userService.updateUser(user);


            modelAndView.addObject("successMessage", "Your account verified successfully. Now please login..");
            //modelAndView.addObject("user", user);
            modelAndView.setViewName("login");

        }
        return modelAndView;
    }
}
