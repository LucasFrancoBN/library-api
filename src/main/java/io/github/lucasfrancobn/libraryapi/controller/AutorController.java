package io.github.lucasfrancobn.libraryapi.controller;

import io.github.lucasfrancobn.libraryapi.controller.dto.AutorDTO;
import io.github.lucasfrancobn.libraryapi.controller.dto.ErroResposta;
import io.github.lucasfrancobn.libraryapi.controller.mapper.AutorMapper;
import io.github.lucasfrancobn.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.lucasfrancobn.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.lucasfrancobn.libraryapi.model.Autor;
import io.github.lucasfrancobn.libraryapi.model.Usuario;
import io.github.lucasfrancobn.libraryapi.service.AutorService;
import io.github.lucasfrancobn.libraryapi.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/autores")
@RequiredArgsConstructor
public class AutorController implements GenericController {
    private final AutorService autorService;
    private final UsuarioService usuarioService;
    private final AutorMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto) {
        var autorEntidade = mapper.toEntity(dto);
        var autorSalvo = autorService.save(autorEntidade);
        URI location = gerarHeaderLocation(autorSalvo.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<AutorDTO> obterDetalhesPorId(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);

        return autorService.obterPorId(idAutor)
                .map(a -> {
                    AutorDTO dto = mapper.toDTO(a);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> remover(@PathVariable("id") String id) {

        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        autorService.deletar(autorOptional.get());

        return ResponseEntity.noContent().build();

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade
    ) {
        List<Autor> resultado = autorService.pesquisaByExameple(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                .stream()
                .map(a -> new AutorDTO(
                        a.getId(),
                        a.getNome()
                        , a.getDataNascimento(),
                        a.getNacionalidade())
                ).toList();

        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO dto) {

        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setId(idAutor);
        autor.setNome(dto.nome());
        autor.setDataNascimento(dto.dataNascimento());
        autor.setNacionalidade(dto.nacionalidade());
        autorService.atualizar(autor);

        return ResponseEntity.noContent().build();
    }
}
