package com.linkedu.it353.controller;

import com.linkedu.it353.model.Balance;
import com.linkedu.it353.model.Role;
import com.linkedu.it353.model.UploadMaterials;
import com.linkedu.it353.model.User;
import com.linkedu.it353.service.BalanceService;
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

import java.util.List;

/**
 * Created by Kaiser on 5/2/2017.
 */
@Controller
public class BalanceController {
    @Autowired
    private UserService userService;

    @Autowired
    private UploadMaterialsService uploadMaterialsService;

    @Autowired
    private BalanceService balanceService;

    @RequestMapping(value = "/recruiter/buycredit", method = RequestMethod.GET)
    public ModelAndView searchStudent() {
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

        Balance balance = balanceService.findBalance(user);

        if (balance != null){
            modelAndView.addObject("balance", balance);
        }else
        {
            Balance balanceIni = new Balance();
            modelAndView.addObject("balance", balance);
        }




        modelAndView.setViewName("recruiter/buybalance");

        return modelAndView;
    }

    @RequestMapping(value = "/recruiter/buycredit", method = RequestMethod.POST)
    public ModelAndView searchStudentPost(Balance balance) {
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

        Balance balanceOld = balanceService.findBalance(user);

        if (balanceOld != null){
            balance.setBalanceId(balanceOld.getBalanceId());
            balance.setBalanceAmount(balanceOld.getBalanceAmount()+balance.getBalanceAmount());
        }

        balance.setUser(user);
        balanceService.saveBalance(balance);

        Balance newBalance = balanceService.findBalance(user);

        modelAndView.addObject("successMessage", "Thank you for purchasing new credits. Your current balance is : ");
        modelAndView.addObject("balance", newBalance);

        modelAndView.setViewName("recruiter/buybalance-success");

        return modelAndView;
    }

}
