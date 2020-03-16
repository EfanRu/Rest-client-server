package com.example.resttemplate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class LoginAndErrorController {

    @GetMapping({"/", "/login"})
    public ModelAndView login(Principal principal) {
        ModelAndView mav = new ModelAndView();
        if (principal == null) {
            mav.setViewName("login");
        } else {
            return new ModelAndView("redirect:/user");
        }
        return mav;
    }

    @GetMapping("/error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        return mav;
    }
}
