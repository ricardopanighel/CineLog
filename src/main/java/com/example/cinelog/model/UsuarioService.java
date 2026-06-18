package com.example.cinelog.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    public void cadastrarUsuario(Usuario usuario) {
        String nome = usuario.getNome() == null ? "" : usuario.getNome().trim();
        String senha = usuario.getPassword() == null ? "" : usuario.getPassword().trim();

        if (nome.isBlank() || senha.isBlank()) {
            throw new IllegalArgumentException("Preencha usuário e senha.");
        }

        if (usuarioDAO.existeUsuarioComNome(nome)) {
            throw new IllegalArgumentException("Esse usuário já existe.");
        }

        usuario.setNome(nome);
        usuario.setPassword(senha);

        usuarioDAO.inserirUsuario(usuario);
        String uuid = usuarioDAO.obterUUID(nome);
        usuarioDAO.inserirPerfil(uuid);
    }
}
