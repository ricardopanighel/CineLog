package com.example.cinelog.model;

public class Usuario {

    private String id;
    private String nome;
    private String password;

    public Usuario() {
    }

    public Usuario(String id, String nome, String password) {
        this.id = id;
        this.nome = nome;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
