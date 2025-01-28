package io.github.lucasfrancobn.libraryapi.controller.mapper;

import io.github.lucasfrancobn.libraryapi.controller.dto.UsuarioDTO;
import io.github.lucasfrancobn.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity(UsuarioDTO usuarioDTO);
}
