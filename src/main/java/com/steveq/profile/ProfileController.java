package com.steveq.profile;

import com.steveq.date.USLocalDateFormatter;
import com.steveq.model.Mock;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * Created by Adam on 2017-05-15.
 */
@Controller
public class ProfileController {

    @ModelAttribute("dateFormat")
    public String localeFormat(Locale locale){
        return USLocalDateFormatter.getPattern(locale);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String displayProfile(ProfileForm profileForm){

        return "profile/profilePage";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String saveProfile(@Valid ProfileForm profileForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "profile/profilePage";
        }
        System.out.println("pomyślnie zapisany profil " + profileForm);
        return "redirect:/profile";
    }
}
