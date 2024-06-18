package org.example.app.springsecuritycontact.auth_app.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.app.springsecuritycontact.apps_dto.ResponseDataDTO;
import org.example.app.springsecuritycontact.auth_app.entitys.entity.User;
import org.example.app.springsecuritycontact.auth_app.services.UserService;
import org.example.app.springsecuritycontact.validate.auth_form.sign_up.SignUpFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AuthController {


    @Autowired
    private UserService userService;


    private static final Logger CONSOLE_LOGGER =
            LogManager.getLogger("console_logger");

    @GetMapping("auth/signUp")
    public String signUp(Model model) {
        model.addAttribute("fragmentName", "sign_up");
        return "index";
    }

    @PostMapping("auth/signUp")
    public RedirectView signUpPost(@ModelAttribute SignUpFormDTO input, RedirectAttributes attributes) {
        CONSOLE_LOGGER.info("Creating new user...");
        ResponseDataDTO<User> createResponse = userService.saveUser(input);
        RedirectView successRedirectView = new RedirectView("/auth/signIn");
        attributes.addFlashAttribute("successMsg", createResponse.getMsg());
        CONSOLE_LOGGER.info("Contacts created successfully.");
        return successRedirectView;
    }

    @GetMapping("auth/signIn")
    public String signIn(Model model) {
        model.addAttribute("fragmentName", "sign_in");
//        model.addAttribute("successMsg", "");
        return "index";
    }

}
