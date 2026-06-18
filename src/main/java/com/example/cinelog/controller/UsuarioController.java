package com.example.cinelog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.cinelog.model.Usuario;
import com.example.cinelog.model.UsuarioService;

@Controller
public class UsuarioController {

    @Autowired
    private ApplicationContext context;

    @GetMapping("/usuario")
    public String formUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "formusuario";
    }

    @PostMapping("/usuario")
    public String cadastrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        UsuarioService usuarioService = context.getBean(UsuarioService.class);
        try {
            usuarioService.cadastrarUsuario(usuario);
            return "redirect:/login?registered";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erroCadastro", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("erroCadastro", "Não foi possível cadastrar o usuário.");
        }
        model.addAttribute("usuario", usuario);
        return "formusuario";
    }
}
