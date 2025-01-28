package io.github.lucasfrancobn.libraryapi.service;

import io.github.lucasfrancobn.libraryapi.model.Usuario;
import io.github.lucasfrancobn.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public void salvarUsuario(Usuario usuario) {
        var senha = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senha);
        usuarioRepository.save(usuario);
    }

    public Usuario obterPorLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }
}
