package io.github.lucasfrancobn.libraryapi.repository.specs;

import io.github.lucasfrancobn.libraryapi.model.GeneroLivro;
import io.github.lucasfrancobn.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {
    public static Specification<Livro> isbnEquals(final String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(final String titulo) {
        return (root, query, cb) ->
                cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEquals(final GeneroLivro genero) {
        return (root, query, cb) -> cb.equal(root.get("genero"), genero);
    }

    public static Specification<Livro> anoPublicacaoEqual(final Integer anoPublicacao) {
        return (root, query, cb) -> cb.equal(
                cb.function("to_char", String.class, root.get("anoPublicacao"), cb.literal("YYYY")
                ), anoPublicacao.toString());
    }

    public static Specification<Livro> nomeAutorLike(final String nomeAutor) {

        return (root, query, cb) -> {
            Join<Object, String> autor = root.join("autor", JoinType.LEFT);
            return cb.like(autor.get("nome"), "%" + nomeAutor + "%");
        };
    }
}
