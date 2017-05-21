package com.steveq.controller;

import com.steveq.profile.UserProfileSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Adam on 2017-05-21.
 */
@Controller
public class HomeController {
    private UserProfileSession userProfileSession;

    @Autowired
    public HomeController(UserProfileSession userProfileSession){
        this.userProfileSession = userProfileSession;
    }

    @RequestMapping("/")
    public String home(){
        List<String> tastes = userProfileSession.getTastes();
        if(tastes.isEmpty()){
            return "redirect:/profile";
        }
        return "redirect:/search/mixed;keywords=" + String.join(",", tastes);
    }
}
