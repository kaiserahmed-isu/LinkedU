package com.linkedu.it353.controller;

/**
 * Created by Kaiser Ahmed on 3/8/2017.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.linkedu.it353.model.User;
import com.linkedu.it353.service.UserService;

@Controller
public class UpdateUserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value={"/upgradeMember"}, method = RequestMethod.POST)
    public ModelAndView upgradeMember(){
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        userService.updateToPaidUser(user);
        modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("memberMessage","Content Available Only for Users with Paid Member Role");
        modelAndView.setViewName("member/home");
        return modelAndView;
    }
}
