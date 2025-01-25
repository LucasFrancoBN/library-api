package io.github.lucasfrancobn.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "livro")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 20, unique = true, nullable = false)
    private String isbn;

    @Column(length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30, nullable = false)
    private GeneroLivro genero;

    @Column(precision = 18, scale = 2)
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualiza")
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;
}
