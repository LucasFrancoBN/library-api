package io.github.lucasfrancobn.libraryapi.controller.mapper;


import io.github.lucasfrancobn.libraryapi.controller.dto.AutorDTO;
import io.github.lucasfrancobn.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {
    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);
}
