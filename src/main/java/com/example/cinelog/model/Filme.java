package com.example.cinelog.model;

// POJO - Plain Old Java Object
public class Filme {

    private String id;
    private String titulo;
    private String genero;
    private int nota;
    private String imagemUrl;
    private String imagemKey;

    public Filme() {
    }

    public Filme(String titulo, String genero, int nota, String imagemUrl, String imagemKey) {
        this.titulo = titulo;
        this.genero = genero;
        this.nota = nota;
        this.imagemUrl = imagemUrl;
        this.imagemKey = imagemKey;
    }

    public Filme(String id, String titulo, String genero, int nota, String imagemUrl, String imagemKey) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.nota = nota;
        this.imagemUrl = imagemUrl;
        this.imagemKey = imagemKey;
    }

    // Getters e Setters
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public String getImagemKey() {
        return imagemKey;
    }

    public void setImagemKey(String imagemKey) {
        this.imagemKey = imagemKey;
    }
}