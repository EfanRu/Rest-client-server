package com.example.resttemplate.controller;

import com.example.resttemplate.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @GetMapping("/user")
    public ModelAndView user(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String principal;
        ModelAndView mav = new ModelAndView();
        try {
            model.addAttribute((User) auth.getPrincipal());
            mav.setViewName("user");
        } catch (ClassCastException e) {
            try {
                User user = new User();
                principal = ((String) auth.getPrincipal());
                String firstName = principal.split(" ")[0];
                String lastName = principal.split(" ")[1];
                if (user.getFirstName() == null && firstName != null) {
                    user.setFirstName(firstName);
                }
                if (user.getLastName() == null && lastName != null) {
                    user.setLastName(lastName);
                }
                model.addAttribute(user);
                mav.setViewName("user");
            } catch (NullPointerException ex) {
                ex.printStackTrace();
                model.addAttribute("msg", "This user don't present in DB");
                mav.setViewName("error");
            }
        }
        return mav;
    }
}
