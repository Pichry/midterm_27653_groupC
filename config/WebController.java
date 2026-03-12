package com.smartsalon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * WebController - serves HTML pages for the frontend
 */
@Controller
public class WebController {

    @GetMapping("/")
    public String home() {
        return "redirect:/welcome.html";
    }

    @GetMapping("/register")
    public String register() {
        return "redirect:/register.html";
    }

    @GetMapping("/login")
    public String login() {
        return "redirect:/login.html";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "redirect:/dashboard.html";
    }

    @GetMapping("/services")
    public String services() {
        return "redirect:/services.html";
    }
}