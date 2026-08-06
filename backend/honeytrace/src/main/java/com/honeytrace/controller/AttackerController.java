package com.honeytrace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AttackerController {

    @GetMapping("/attackers")
    public String attackers() {

        return "attackers";

    }

}