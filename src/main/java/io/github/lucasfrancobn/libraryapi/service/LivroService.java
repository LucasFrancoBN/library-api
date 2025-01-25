package io.github.lucasfrancobn.libraryapi.service;

import io.github.lucasfrancobn.libraryapi.model.GeneroLivro;
import io.github.lucasfrancobn.libraryapi.model.Livro;
import io.github.lucasfrancobn.libraryapi.repository.LivroRepository;
import io.github.lucasfrancobn.libraryapi.repository.specs.LivroSpecs;
import io.github.lucasfrancobn.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.lucasfrancobn.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {
    private final LivroRepository livroRepository;
    private final LivroValidator livroValidator;

    public Livro salvar(Livro livro) {
        livroValidator.validar(livro);
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro) {
        livroRepository.delete(livro);
    }

    public Page<Livro> pesquisa(
            String isbn,
            String titulo,
            String nomeAutor,
            GeneroLivro genero,
            Integer anoPublicacao,
            Integer pagina,
            Integer tamanhoPagina
    ) {
        // O conjunction retorna uma query verdadeira independente se tivermos recebido algum valor ou não
        Specification<Livro> specs = Specification
                .where((root, query, cb) -> cb.conjunction());

        if(isbn != null) {
            specs = specs.and(isbnEquals(isbn));
        }

        if(titulo != null) {
            specs = specs.and(tituloLike(titulo));
        }

        if(genero != null) {
            specs = specs.and(generoEquals(genero));
        }

        if(anoPublicacao != null) {
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }

        if(nomeAutor != null) {
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);

        return livroRepository.findAll(specs, pageable);
    }

    public void atualizar(Livro livro) {
        if(livro.getId() == null) {
            throw new IllegalArgumentException("Para atualizar, é necessário que o livro esteja salvo");
        }

        livroValidator.validar(livro);
        livroRepository.save(livro);
    }
}
