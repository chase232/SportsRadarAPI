package com.oreillyauto.finalproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WidgetController extends BaseController {

    @GetMapping(value = {"finalproject"})
    public String getWidget(Model model) throws Exception {
        System.out.println("Got here");
        return "widget";
    }
}







