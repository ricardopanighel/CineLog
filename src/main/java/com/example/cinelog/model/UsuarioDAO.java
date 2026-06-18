package com.example.cinelog.model;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class UsuarioDAO {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private JdbcTemplate jdbc;

    @PostConstruct
    private void initialize() {
        jdbc = new JdbcTemplate(dataSource);
    }

    public void inserirUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario(nome, password) VALUES (?, ?)";
        Object[] parametros = new Object[2];
        parametros[0] = usuario.getNome();
        parametros[1] = passwordEncoder.encode(usuario.getPassword());
        jdbc.update(sql, parametros);
    }

    public String obterUUID(String nome) {
        String sql = "SELECT id::text FROM usuario WHERE nome = ?";
        return jdbc.queryForObject(sql, String.class, nome);
    }

    public void inserirPerfil(String uuid) {
        String sql = "INSERT INTO perfil(usuarioid, cargo) VALUES (?::uuid, ?) " +
                "ON CONFLICT (usuarioid) DO UPDATE SET cargo = EXCLUDED.cargo";
        Object[] parametros = new Object[2];
        parametros[0] = uuid;
        parametros[1] = "usuario";
        jdbc.update(sql, parametros);
    }

    public boolean existeUsuarioComNome(String nome) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE nome = ?";
        Integer quantidade = jdbc.queryForObject(sql, Integer.class, nome);
        return quantidade != null && quantidade > 0;
    }
}
