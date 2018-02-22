package com.tour.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController  {


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String tourists( String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }


}
