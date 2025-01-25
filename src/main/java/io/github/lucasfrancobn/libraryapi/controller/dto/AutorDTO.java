package io.github.lucasfrancobn.libraryapi.controller.dto;

import io.github.lucasfrancobn.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "Esse campo não pode ser vazio")
        @Size(min = 2, max = 100, message = "Campo fora do tamanho padrão")
        String nome,
        @NotNull(message = "Esse campo não pode ser nulo")
        @Past(message = "Não pode ser uma data futura")
        LocalDate dataNascimento,
        @NotBlank(message = "Esse campo não pode ser vazio")
        @Size(min = 2, max = 50, message = "Campo fora do tamanho padrão")
        String nacionalidade
) {
}
