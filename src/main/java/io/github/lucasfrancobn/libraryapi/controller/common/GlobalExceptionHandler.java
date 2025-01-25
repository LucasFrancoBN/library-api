package io.github.lucasfrancobn.libraryapi.controller.common;

import io.github.lucasfrancobn.libraryapi.controller.dto.ErroCampo;
import io.github.lucasfrancobn.libraryapi.controller.dto.ErroResposta;
import io.github.lucasfrancobn.libraryapi.exceptions.CampoInvalidoException;
import io.github.lucasfrancobn.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.lucasfrancobn.libraryapi.exceptions.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<ErroCampo> listaErros = fieldErrors
                .stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .toList();

        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação",
                listaErros
        );
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoException(final RegistroDuplicadoException exception) {
        return ErroResposta.conflito(exception.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPermitida(final OperacaoNaoPermitidaException exception) {
        return ErroResposta.respostaPadrao(exception.getMessage());
    }

    @ExceptionHandler(CampoInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleCampoInvalidoException(final CampoInvalidoException exception) {
        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação.",
                List.of(new ErroCampo(exception.getCampo(), exception.getMessage()))

        );
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErroResposta handleErrosNaoTratados(Exception exception) {
//        return new ErroResposta(
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                "Ocorreu um erro inesperado. Entre em contato com a administração",
//                List.of(new ErroCampo("erro", exception.getMessage()))
//        );
//    }
}
