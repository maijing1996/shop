package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/page/manager")
public class ManagerModel {

	@RequestMapping("/to")
    public ModelAndView toAppley(){
        return new ModelAndView("/one");
    }
}
