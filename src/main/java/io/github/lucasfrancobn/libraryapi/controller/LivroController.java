package io.github.lucasfrancobn.libraryapi.controller;

import io.github.lucasfrancobn.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.lucasfrancobn.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.lucasfrancobn.libraryapi.controller.mapper.LivroMapper;
import io.github.lucasfrancobn.libraryapi.model.GeneroLivro;
import io.github.lucasfrancobn.libraryapi.model.Livro;
import io.github.lucasfrancobn.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/livros")
@RequiredArgsConstructor
@Slf4j
public class LivroController implements GenericController {
    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Void> salvarLivro(@RequestBody @Valid CadastroLivroDTO request) {
        log.info("Salvando livro: {}", request);
        Livro livro = livroMapper.toEntity(request);
        livroService.salvar(livro);
        var url = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(url).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable UUID id) {
        return livroService.obterPorId(id)
                .map(livro -> {
                    var dto = livroMapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Object> deletar(@PathVariable UUID id) {
        log.info("Deletando livro: {}", id);
        return livroService.obterPorId(id)
                .map(l -> {
                    livroService.deletar(l);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisa(
           @RequestParam(value = "isbn", required = false)
           String isbn,
           @RequestParam(value = "titulo", required = false)
           String titulo,
           @RequestParam(value = "nome-autor", required = false)
           String nomeAutor,
           @RequestParam(value = "genero", required = false)
           GeneroLivro genero,
           @RequestParam(value = "ano-publicacao", required = false)
           Integer anoPublicacao,
           @RequestParam(value = "pagina", defaultValue = "0")
           Integer pagina,
           @RequestParam(value = "tamanho-pagina", defaultValue = "10")
           Integer tamanhoPagina
    ) {
        var resultado = livroService.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);
        var lista = resultado.map(livroMapper::toDTO);

        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Object> atualizar(@PathVariable UUID id, @RequestBody @Valid CadastroLivroDTO request) {
        log.info("Atualizando livro com id {} e dados {}", id, request);
        return livroService.obterPorId(id)
                .map(livro -> {
                    Livro entidadeAux = livroMapper.toEntity(request);
                    livro.setId(id);
                    livro.setDataPublicacao(entidadeAux.getDataPublicacao());
                    livro.setTitulo(entidadeAux.getTitulo());
                    livro.setGenero(entidadeAux.getGenero());
                    livro.setTitulo(entidadeAux.getTitulo());
                    livro.setAutor(entidadeAux.getAutor());
                    livro.setIsbn(entidadeAux.getIsbn());

                    livroService.atualizar(livro);

                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
