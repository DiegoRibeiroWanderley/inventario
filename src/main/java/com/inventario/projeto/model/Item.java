package com.inventario.projeto.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "O item deve conter um SKU")
    private String SKU;

    @NotEmpty(message = "O item deve conter um código de barras")
    private String codigoDeBarras;

    @NotEmpty(message = "O item deve conter um nome")
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
    }
}