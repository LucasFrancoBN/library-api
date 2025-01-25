package io.github.lucasfrancobn.libraryapi.repository;

import io.github.lucasfrancobn.libraryapi.model.Autor;
import io.github.lucasfrancobn.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {
    boolean existsByAutor(Autor autor);

    Optional<Livro> findByIsbn(String isbn);
}
