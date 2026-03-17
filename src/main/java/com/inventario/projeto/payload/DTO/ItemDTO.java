package com.inventario.projeto.payload.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private Integer id;
    private String SKU;
    private String codigoDeBarras;

    private String nome;
    private String descricao;
    private CategoriaDTO categoriaDTO;
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

    private LocalDate criadoEm = LocalDate.now();
    private LocalDate ultimoUpdate;
    private Boolean ativo;
}
