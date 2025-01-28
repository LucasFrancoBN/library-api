package io.github.lucasfrancobn.libraryapi.service;

import io.github.lucasfrancobn.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.lucasfrancobn.libraryapi.model.Autor;
import io.github.lucasfrancobn.libraryapi.model.Usuario;
import io.github.lucasfrancobn.libraryapi.repository.AutorRepository;
import io.github.lucasfrancobn.libraryapi.repository.LivroRepository;
import io.github.lucasfrancobn.libraryapi.security.SecurityService;
import io.github.lucasfrancobn.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {
    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;
    private final SecurityService securityService;


    public Autor save(Autor autor) {
        Usuario usuario = securityService.obterUsuarioLogado();
        autor.setUsuario(usuario);
        return repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Autor autor) {
        if(possuiLivro(autor)) {
            throw new OperacaoNaoPermitidaException("Não é permitido excluir um Autor que possui livros cadastrados");
        }

        repository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade) {
        if(nome != null && nacionalidade != null) {
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        } else if(nome != null) {
            return repository.findByNome(nome);
        } else if(nacionalidade != null) {
            return repository.findByNacionalidade(nacionalidade);
        }

        return repository.findAll();
    }

    public List<Autor> pesquisaByExameple(String nome, String nacionalidade) {
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Autor> autorExample = Example.of(autor, matcher);

        return repository.findAll(autorExample);
    }

    public void atualizar(Autor autor) {
        if(autor.getId() == null) {
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor tenha um ID");
        }
        validator.validar(autor);

        Usuario usuario = securityService.obterUsuarioLogado();
        autor.setUsuario(usuario);

        repository.save(autor);
    }

    public boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }
}
