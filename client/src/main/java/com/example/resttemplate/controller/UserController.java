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
        Authentication auth;
        ModelAndView mav = new ModelAndView();
        try {
            auth = SecurityContextHolder.getContext().getAuthentication();
            Object user = auth.getPrincipal();
            model.addAttribute(user);
        } catch (ClassCastException e) {
            e.printStackTrace();
            model.addAttribute("msg", "This user don't present in DB");
            mav.setViewName("error");
        }
        mav.setViewName("user");
        return mav;
    }
}
