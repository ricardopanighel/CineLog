package com.example.cinelog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.cinelog.model.Filme;
import com.example.cinelog.model.FilmeService;

@Controller
public class PaginaController {

    @Autowired
    private ApplicationContext context;

    @GetMapping("/")
    public String paginaInicial() {
        return "index";

    }

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("filme", new Filme());
        return "formfilme";
    }

    @GetMapping("/sucesso")
    public String sucesso() {
        return "sucesso";
    }

    @PostMapping("/filme")
    public String cadastro(Model model, @ModelAttribute Filme filme) {
        // PARA CADA TABELA, 1 SERVICE e 1 DAO, ALEM DE 1 POJO
        FilmeService as = context.getBean(FilmeService.class);
        as.inserirFilme(filme);
        return "redirect:/sucesso";
    }

    @GetMapping("/lista")
    public String listar(Model model) {
        FilmeService as = context.getBean(FilmeService.class);
        List<Filme> lista = as.listarTodos(); // Agora o 'as' funciona aqui
        model.addAttribute("listaFilmes", lista);
        return "lista";
    }

    @GetMapping("/lista/{id}")
	public String listarFilme(@PathVariable String id, Model model){
		FilmeService as = context.getBean(FilmeService.class);
		Filme filme = as.mostrarFilme(id);
		model.addAttribute("filme", filme);
		return "filme";
	}
}
