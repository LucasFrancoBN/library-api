package io.github.lucasfrancobn.libraryapi.validator;

import io.github.lucasfrancobn.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.lucasfrancobn.libraryapi.model.Autor;
import io.github.lucasfrancobn.libraryapi.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AutorValidator {
    private final AutorRepository autorRepository;

    public void validar(Autor autor) {
        if(existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado");
        }
    }

    private boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorEncontrado = autorRepository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()
        );

        if(autor.getId() != null) {
            return autorEncontrado.isPresent();
        }

        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }
}
