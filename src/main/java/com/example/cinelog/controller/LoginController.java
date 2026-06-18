package com.example.cinelog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "registered", required = false) String registered,
            Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "Usuário ou senha inválidos.");
        }
        if (logout != null) {
            model.addAttribute("logoutMsg", "Sessão encerrada com sucesso.");
        }
        if (registered != null) {
            model.addAttribute("registeredMsg", "Cadastro realizado com sucesso. Faça login.");
        }
        return "login";
    }
}
