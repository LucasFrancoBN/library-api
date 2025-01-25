package io.github.lucasfrancobn.libraryapi.validator;

import io.github.lucasfrancobn.libraryapi.exceptions.CampoInvalidoException;
import io.github.lucasfrancobn.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.lucasfrancobn.libraryapi.model.Livro;
import io.github.lucasfrancobn.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {
    private final LivroRepository livroRepository;
    private static int ANO_EXIGENCIA_PRECO = 2020;

    public void validar(Livro livro) {
        if(existeLivroComIsbn(livro)) {
            throw new RegistroDuplicadoException("ISBN já cadastrado");
        }

        if(isPrecoObrigatorioNulo(livro)) {
            throw new CampoInvalidoException("preco", "Para livros com ano de publicação a partir de 2020, o preço é obrigatorio");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null &&
                livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    private boolean existeLivroComIsbn(Livro livro) {
        Optional<Livro> livroComIsbn = livroRepository.findByIsbn(livro.getIsbn());

        if(livro.getId() != null) {
            return livroComIsbn.isPresent();
        }

        return livroComIsbn.map(Livro::getId).stream().anyMatch(id -> !id.equals(livro.getId()));
    }
}
