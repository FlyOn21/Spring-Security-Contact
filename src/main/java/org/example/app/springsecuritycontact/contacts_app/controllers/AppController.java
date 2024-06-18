package org.example.app.springsecuritycontact.contacts_app.controllers;

import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AppController {

    private static final Logger CONSOLE_LOGGER =
            LogManager.getLogger("console_logger");

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        CONSOLE_LOGGER.info("Home page requested {}.", session.getAttribute("logoutMsg"));
        model.addAttribute("title", "Customers CRM");
        model.addAttribute("fragmentName", "home");
        return "index";
    }
}