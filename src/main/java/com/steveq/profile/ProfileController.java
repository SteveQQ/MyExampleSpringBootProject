package com.steveq.profile;

import com.steveq.date.USLocalDateFormatter;
import com.steveq.model.Mock;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserProfileSession userProfileSession;

    @ModelAttribute("dateFormat")
    public String localeFormat(Locale locale){
        return USLocalDateFormatter.getPattern(locale);
    }

    @ModelAttribute()
    public ProfileForm getProfileForm(){
        return userProfileSession.toForm();
    }

    @Autowired
    public ProfileController(UserProfileSession userProfileSession){
        this.userProfileSession = userProfileSession;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String displayProfile(ProfileForm profileForm){

        return "profile/profilePage";
    }

    @RequestMapping(value = "/profile", params = {"save"}, method = RequestMethod.POST)
    public String saveProfile(@Valid ProfileForm profileForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "profile/profilePage";
        }
        userProfileSession.saveForm(profileForm);
        return "redirect:/search/mixed;keywords=" + String.join(",", profileForm.getTastes());
    }

    @RequestMapping(value = "/profile", params = {"addTaste"})
    public String addRow(ProfileForm profileForm){
        profileForm.getTastes().add(null);
        return "profile/profilePage";
    }

    @RequestMapping(value = "/profile", params = {"removeTaste"})
    public String removeRow(ProfileForm profileForm, HttpServletRequest req){
        Integer rowId = Integer.valueOf(req.getParameter("removeTaste"));
        profileForm.getTastes().remove(rowId.intValue());
        return "profile/profilePage";
    }
}
