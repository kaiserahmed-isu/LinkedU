package com.linkedu.it353.controller;

import com.linkedu.it353.model.UniversityProgram;
import com.linkedu.it353.model.User;
import com.linkedu.it353.service.UniversityProgramService;
import com.linkedu.it353.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by sanket on 4/18/2017.
 */
@Controller
public class UniversityProgramController {
    @Autowired
    private UniversityProgramService universityProgramService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/programs", method = RequestMethod.GET)
    public ModelAndView getAllPrograms() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("programs", universityProgramService.getAllPrograms());
        modelAndView.setViewName("university/program/programs");
        return modelAndView;
    }

    @RequestMapping("/program/{program_id}")
    public ModelAndView getProgram(@PathVariable Integer program_id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("programs", universityProgramService.getProgram(program_id));
        modelAndView.setViewName("university/program/program");
        return modelAndView;
    }

    @RequestMapping(value = "/programupdate", method = RequestMethod.GET)
    public ModelAndView programRegistration() {
        ModelAndView modelAndView = new ModelAndView();
        UniversityProgram universityProgram = new UniversityProgram();
        modelAndView.addObject("program", universityProgram);
        modelAndView.setViewName("university/universityProgram/programupdate");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/program")
    public ModelAndView updateUniversity(@Valid UniversityProgram universityProgram) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        universityProgram.setUser_id(user.getId());//needs to be updated
        universityProgramService.addProgram(universityProgram);
        modelAndView.setViewName("university/universityProgram/successful");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/updateprogram/{name}")
    public void updateProgram(@RequestBody UniversityProgram universityProgram, @PathVariable Integer name) {
        universityProgramService.updateProgram(name, universityProgram);
    }
}
