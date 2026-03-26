package com.example.cinelog.model;

// POJO - Plain Old Java Object
public class Filme {

    private String id;
    private String titulo;
    private String genero;
    private int nota;

    public Filme() {
    }

    public Filme(String titulo, String genero, int nota) {
        this.titulo = titulo;
        this.genero = genero;
        this.nota = nota;
    }

    public Filme(String id, String titulo, String genero, int nota) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.nota = nota;
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
}