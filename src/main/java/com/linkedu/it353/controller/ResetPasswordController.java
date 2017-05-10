package com.linkedu.it353.controller;

import com.linkedu.it353.mail.MailClient;
import com.linkedu.it353.model.User;
import com.linkedu.it353.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.UUID;

@Controller
public class ResetPasswordController {

	@Autowired
    private UserService userService;

    @Autowired
    private MailClient mailClient;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static String SITE_ROOT_LINK = "https://linkedu.herokuapp.com";
	
	@RequestMapping(value="/forget-password", method = RequestMethod.GET)
    public ModelAndView forgetPassword(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("forget-password");
        return modelAndView;
    }

    @RequestMapping(value = "/forget-password", method = RequestMethod.POST)
    public ModelAndView resetPassword(@RequestParam("email") String email) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByEmail(email);
        if (user == null) {
        	modelAndView.addObject("errorMessage", "Email not found, please check your email again.");
            modelAndView.setViewName("forget-password");
        } else {
        	String uuid = UUID.randomUUID().toString();
            user.setValidateCode(uuid);
            userService.saveUser(user);

            //given
            String recipient = user.getEmail();
            String subject = "Reset password - LinkedU";
            String message = "Please click here "+SITE_ROOT_LINK+"/resetPassword/"+uuid+" . Thank you! - LinkedU";
            //when
            mailClient.prepareAndSend(recipient, subject, message);

            modelAndView.addObject("successMessage", "Please check your email to reset your password");
            modelAndView.setViewName("forget-password");


        }
        return modelAndView;
    }
    
    @RequestMapping(value = "/resetPassword/{code}", method = RequestMethod.GET)
    public ModelAndView verifyResetCode(@PathVariable("code") String code) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByValidateCode(code);
        if (user == null)  {
            modelAndView.addObject("successMessage", "Sorry, code is not valid.");
            modelAndView.setViewName("login");
        } else {
            modelAndView.addObject("code", code);
            modelAndView.setViewName("reset-password");
        }
        return modelAndView;
    }
    
    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public ModelAndView verifyStudent(@RequestParam("code") String code, @RequestParam("password") String password, @RequestParam("password2") String password2) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByValidateCode(code);
        if (user == null)  {
            modelAndView.addObject("errorMessage", "Sorry, code is not valid.");
            modelAndView.setViewName("reset-password");
        } 
        
        else if(password.length()<5){
        	modelAndView.addObject("errorMessage", "Password should be more than 5 characters.");
        	modelAndView.addObject("code", code);
            modelAndView.setViewName("reset-password");
        }
        
        else if(!password.equals(password2)){
        	modelAndView.addObject("errorMessage", "Password does not match. Please check again");
        	modelAndView.addObject("code", code);
            modelAndView.setViewName("reset-password");
        }
        
        else {
            String uuid = null;
            user.setValidateCode(uuid);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            userService.saveUser(user);
            

            modelAndView.addObject("successMessage", "Your password was reset successfully. Now please login..");
            //modelAndView.addObject("user", user);
            modelAndView.setViewName("login");

        }
        return modelAndView;
    }
}
