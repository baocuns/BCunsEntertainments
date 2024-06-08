package com.cuns.bce.controllers;

import com.cuns.bce.dto.auth.UserDto;
import com.cuns.bce.dto.request.auth.UserRegisterDto;
import com.cuns.bce.entities.User;
import com.cuns.bce.services.impl.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class DefaultController {

    public final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @GetMapping("/")
    public String home() {
        return "index";
    }
    @GetMapping("login")
    public String showLoginPage(Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);

//        log.info("user" + user.get().getUsername());
//        log.info("Login form: " + passwordEncoder.encode("123456"));
        return "pages/auth/login";
    }
    @GetMapping("register")
    public String showRegisterPage(Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        model.addAttribute("user", userRegisterDto);
        return "pages/auth/register";
    }
    @PostMapping("register")
    public String registerNewUserAccount(@ModelAttribute("user") UserRegisterDto userRegisterDto, Model model) {
        try {
            userService.registerNewUserAccount(userRegisterDto);
            model.addAttribute("registerSuccess", "Register successfully!");
            model.addAttribute("user", userRegisterDto);
            return "pages/auth/login";
        } catch (Exception e) {
            model.addAttribute("errorRegister", e.getMessage());
            e.printStackTrace();
            return "pages/auth/register";
        }
    }
    @GetMapping("forgot-password")
    public String showForgotPasswordPage() {
        return "pages/auth/forgot-password";
    }
    @GetMapping("history")
    public String showHistoryPage() {
        return "pages/history";
    }
}
