package com.linkedu.it353.controller;

import com.linkedu.it353.model.Showcase;
import com.linkedu.it353.model.UniversityProfile;
import com.linkedu.it353.model.UploadMaterials;
import com.linkedu.it353.model.User;
import com.linkedu.it353.service.ShowcaseService;
import com.linkedu.it353.service.UniversityProfileService;
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

import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sanket on 4/21/2017.
 */
@Controller
public class ShowcaseController {
    @Autowired
    private ShowcaseService showcaseService;
    @Autowired
    private UniversityProfileService universityProfileService;

    @Autowired
    private UserService userService;

    @Autowired
    private UploadMaterialsService uploadMaterialsService;

    @RequestMapping(value = "/admin/showcase", method = RequestMethod.GET)
    public ModelAndView forShowcase() {
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

        List<UniversityProfile> profileList = universityProfileService.getAllUniversities();
        List<Showcase> showcases = showcaseService.getAllShowcased();
        List<Integer> list = showcases.stream().map(Showcase::getUniversity_id).collect(Collectors.toList());
        Iterator<UniversityProfile> profileIterator = profileList.iterator();
        while (profileIterator.hasNext()) {
            UniversityProfile profile = profileIterator.next();
            if (list.contains(profile.getUser_id())) {
                profileIterator.remove();
            }
        }
        modelAndView.addObject("universities", profileList);
        modelAndView.addObject("showcases", showcases);
        modelAndView.addObject("showcaseDel", new Showcase());
//        System.out.println("two");
        modelAndView.addObject("showcase", new Showcase());
//        System.out.println("three");


//        System.out.println("four");
        modelAndView.setViewName("admin/Showcase");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin/showcase")
    public ModelAndView postShowcase(@Valid Showcase showcase) {
        ModelAndView modelAndView = new ModelAndView();
        // showcase.setUniversity_id(5000);//needs to be updated
        showcaseService.addToShowcase(showcase);
        //        modelAndView.setViewName("university/profile/successful");
        modelAndView.setViewName("redirect:/admin/showcase");
        return modelAndView;
    }


    @RequestMapping(value = "/admin/delete/showcase", method = RequestMethod.POST)
    public ModelAndView getUniversity(@RequestParam("showcaseId") Integer showcaseId) {
        showcaseService.removeShowcase(showcaseId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/showcase");
        return modelAndView;
    }
}
