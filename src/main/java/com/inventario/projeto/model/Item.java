package com.inventario.projeto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "itens")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String SKU;
    private String codigoDeBarras;
    private String nome;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    private String marca;

    private Integer quantidadeEmEstoque;
    private Integer quantidadeMinima;
    private Double peso;

    private Double precoCompra;
    private Double precoVenda;
    private Double taxa;
    private Double valorGanho;

    private Integer saidas;
    private Integer entradas;

    private LocalDate criadoEm;
    private LocalDate ultimoUpdate;
    private Boolean ativo;

    @PrePersist
    public void prePersist() {
        if (criadoEm == null) criadoEm = LocalDate.now();
        if (ativo == null) ativo = true;
        if (saidas == null) saidas = 0;
        if (entradas == null) entradas = 0;
        if (quantidadeEmEstoque == null) quantidadeEmEstoque = 0;
        if (quantidadeMinima == null) quantidadeMinima = 0;
        if (precoCompra == null) precoCompra = 0.0;
        if (precoVenda == null) precoVenda = 0.0;
        if (taxa == null) taxa = 0.0;
    }
}