package io.github.lucasfrancobn.libraryapi.controller.dto;

public record ErroCampo(
        String campo,
        String erro
) {
}
