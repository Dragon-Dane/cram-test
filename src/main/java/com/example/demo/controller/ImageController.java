package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ImageController {

    @RequestMapping("view")
    public String viewProducts() {
        return "view";
    }



}
