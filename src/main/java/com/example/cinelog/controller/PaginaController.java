package com.example.cinelog.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.cinelog.model.BucketStorageService;
import com.example.cinelog.model.Filme;
import com.example.cinelog.model.FilmeService;
import com.example.cinelog.model.StorageUploadResult;

@Controller
public class PaginaController {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private BucketStorageService bucketStorageService;

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
    public String cadastro(Model model,
            @ModelAttribute Filme filme,
            @RequestParam("imagem") MultipartFile imagem) {
        // PARA CADA TABELA, 1 SERVICE e 1 DAO, ALEM DE 1 POJO
        FilmeService as = context.getBean(FilmeService.class);
        try {
            anexarImagemSeEnviada(filme, imagem, null);
        } catch (IOException e) {
            model.addAttribute("erroUpload", "Falha ao enviar imagem para o bucket.");
            model.addAttribute("filme", filme);
            return "formfilme";
        }
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
    public String listarFilme(@PathVariable String id, Model model) {
        FilmeService as = context.getBean(FilmeService.class);
        Filme filme = as.mostrarFilme(id);
        if (filme == null) {
            return "redirect:/lista";
        }
        model.addAttribute("filme", filme);
        return "filme";
    }

    @GetMapping("/filme/{id}/editar")
    public String formAtualizarFilme(@PathVariable("id") String id, Model model) {
        FilmeService as = context.getBean(FilmeService.class);
        Filme filmeId = as.mostrarFilme(id);
        if (filmeId == null) {
            return "redirect:/lista";
        }
        model.addAttribute("filme", filmeId);
        model.addAttribute("id", id);
        return "formupdfilme";
    }

    @PostMapping("/filme/{id}/editar")
    public String atualizarFilme(@PathVariable("id") String id,
            Model model,
            @ModelAttribute Filme filme,
            @RequestParam("imagem") MultipartFile imagem) {
        FilmeService as = context.getBean(FilmeService.class);
        Filme filmeAtual = as.mostrarFilme(id);
        if (filmeAtual == null) {
            return "redirect:/lista";
        }

        filme.setImagemUrl(filmeAtual.getImagemUrl());
        filme.setImagemKey(filmeAtual.getImagemKey());

        try {
            anexarImagemSeEnviada(filme, imagem, filmeAtual.getImagemKey());
        } catch (IOException e) {
            model.addAttribute("erroUpload", "Falha ao enviar imagem para o bucket.");
            model.addAttribute("filme", filme);
            model.addAttribute("id", id);
            return "formupdfilme";
        }

        as.atualizarFilme(filme, id);
        return "redirect:/lista/" + id;
    }

    @PostMapping("/filme/{id}/deletar")
    public String deletarFilme(@PathVariable("id") String id) {
        FilmeService as = context.getBean(FilmeService.class);
        Filme filme = as.mostrarFilme(id);
        if (filme == null) {
            return "redirect:/lista";
        }
        try {
            bucketStorageService.deletarImagem(filme.getImagemKey());
        } catch (IOException e) {
            throw new RuntimeException("Falha ao remover imagem do bucket.", e);
        }
        as.deletarFilme(id);
        return "redirect:/lista";
    }

    @GetMapping("/bucket/{chaveArquivo}")
    public ResponseEntity<Resource> servirImagem(@PathVariable String chaveArquivo) throws IOException {
        Resource resource = bucketStorageService.carregar(chaveArquivo);
        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }
        String contentType = bucketStorageService.contentType(chaveArquivo);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    private void anexarImagemSeEnviada(Filme filme, MultipartFile imagem, String imagemAntiga) throws IOException {
        if (imagem == null || imagem.isEmpty()) {
            return;
        }
        StorageUploadResult upload = bucketStorageService.uploadImagem(imagem);
        filme.setImagemKey(upload.chaveArquivo());
        filme.setImagemUrl(upload.urlPublica());
        if (imagemAntiga != null && !imagemAntiga.isBlank()) {
            bucketStorageService.deletarImagem(imagemAntiga);
        }
    }
}
