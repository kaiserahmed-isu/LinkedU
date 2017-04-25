package com.linkedu.it353.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Kaiser on 4/25/2017.
 */
@Controller
public class PageController {

    @RequestMapping(value={"/contact-us"}, method = RequestMethod.GET)
    public ModelAndView contactUs(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/contactus");
        return modelAndView;
    }

    @RequestMapping(value={"/about-us"}, method = RequestMethod.GET)
    public ModelAndView aboutUs(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/aboutus");
        return modelAndView;
    }

    @RequestMapping(value={"/terms"}, method = RequestMethod.GET)
    public ModelAndView terms(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/terms");
        return modelAndView;
    }

    @RequestMapping(value={"/policies"}, method = RequestMethod.GET)
    public ModelAndView policies(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/policies");
        return modelAndView;
    }
}
