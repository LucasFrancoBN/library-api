package io.github.lucasfrancobn.libraryapi.controller;

import io.github.lucasfrancobn.libraryapi.controller.dto.UsuarioDTO;
import io.github.lucasfrancobn.libraryapi.controller.mapper.UsuarioMapper;
import io.github.lucasfrancobn.libraryapi.model.Usuario;
import io.github.lucasfrancobn.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Slf4j
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarUsuario(@RequestBody UsuarioDTO request) {
        log.info("Iniciando cadastro de usuario: {}", request);
        var usuario = usuarioMapper.toEntity(request);
        usuarioService.salvarUsuario(usuario);
    }
}
