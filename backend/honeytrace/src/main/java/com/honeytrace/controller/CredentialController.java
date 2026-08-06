package com.honeytrace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CredentialController {

    @GetMapping("/credentials")
    public String credentials(){

        return "credentials";

    }

}