package io.github.lucasfrancobn.libraryapi.controller.mapper;

import io.github.lucasfrancobn.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.lucasfrancobn.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.lucasfrancobn.libraryapi.model.Livro;
import io.github.lucasfrancobn.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {AutorMapper.class})
public abstract class LivroMapper {
    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
