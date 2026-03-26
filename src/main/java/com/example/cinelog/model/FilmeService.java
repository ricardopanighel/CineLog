package com.example.cinelog.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilmeService {

    @Autowired // cria a bagaca pra mim!
    FilmeDAO fDao;

    public void inserirFilme(Filme cli) {
        fDao.inserirFilme(cli);
    }

    public Filme mostrarFilme(String id){
        return fDao.mostrarFilme(id);
    }

    public List<Filme> listarTodos() {
        return fDao.listarFilmes();
    }

}
