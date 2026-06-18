
package com.example.cinelog.model;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
        String sql = "INSERT INTO filmes(titulo, genero, nota, imagem_url, imagem_key) VALUES(?,?,?,?,?)";

        // Criamos o array com 5 posições, uma para cada '?'
        Object[] parametros = new Object[5];
        parametros[0] = filme.getTitulo();
        parametros[1] = filme.getGenero();
        parametros[2] = filme.getNota();
        parametros[3] = filme.getImagemUrl();
        parametros[4] = filme.getImagemKey();
        jdbc.update(sql, parametros);
    }

    public void atualizarFilme(Filme filme, String id) {
        String sql = "UPDATE filmes SET titulo = ?, genero = ?, nota = ?, imagem_url = ?, imagem_key = ? WHERE id = ?::uuid";
        Object[] parametros = new Object[6];
        parametros[0] = filme.getTitulo();
        parametros[1] = filme.getGenero();
        parametros[2] = filme.getNota();
        parametros[3] = filme.getImagemUrl();
        parametros[4] = filme.getImagemKey();
        parametros[5] = id;
        jdbc.update(sql, parametros);
    }

    public void deletarFilme(String id) {
        String sql = "DELETE FROM filmes WHERE id = ?::uuid";
        jdbc.update(sql, id);
    }

    public Filme mostrarFilme(String id) {
        String sql = "SELECT * FROM filmes where id=?::uuid";

        try {
            Map<String, Object> linha = jdbc.queryForMap(sql, id);
            return new Filme(
                    linha.get("id").toString(),
                    (String) linha.get("titulo"),
                    (String) linha.get("genero"),
                    ((Number) linha.get("nota")).intValue(),
                    (String) linha.get("imagem_url"),
                    (String) linha.get("imagem_key"));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Filme> listarFilmes() {
        String sql = "SELECT * FROM filmes";

        return jdbc.query(sql, (rs, rowNum) -> {
            return new Filme(
                    rs.getString("id"),
                    rs.getString("titulo"),
                    rs.getString("genero"),
                    rs.getInt("nota"),
                    rs.getString("imagem_url"),
                    rs.getString("imagem_key"));
        });
    }
}
