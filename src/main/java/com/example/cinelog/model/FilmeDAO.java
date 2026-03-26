
package com.example.cinelog.model;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class FilmeDAO {

    @Autowired
    DataSource dataSource;

    JdbcTemplate jdbc;

    @PostConstruct
    private void initialize() {
        jdbc = new JdbcTemplate(dataSource);
    }

    public void inserirFilme(Filme filme) {
        String sql = "INSERT INTO filmes(titulo, genero, nota) VALUES(?,?,?)";

        // Criamos o array com 3 posições uma para cada '?'
        Object[] parametros = new Object[3];
        parametros[0] = filme.getTitulo();
        parametros[1] = filme.getGenero();
        parametros[2] = filme.getNota();
        jdbc.update(sql, parametros);
    }

    public Filme mostrarFilme(String id){
        String sql = "SELECT * FROM filmes where id=?::uuid";
        
        return jdbc.queryForObject(sql, (rs, rowNum) -> {
            return new Filme(
                rs.getString("titulo"),
                rs.getString("genero"),
                rs.getInt("nota"));
        }, id);
	}

    public List<Filme> listarFilmes() {
        String sql = "SELECT * FROM filmes";

        return jdbc.query(sql, (rs, rowNum) -> {
            return new Filme(
                    rs.getString("id"),
                    rs.getString("titulo"),
                    rs.getString("genero"),
                    rs.getInt("nota"));
        });
    }
}
