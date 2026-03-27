package com.inventario.projeto.payload.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private Integer id;

    @NotBlank(message = "O item deve conter um SKU")
    private String SKU;

    @NotBlank(message = "O item deve conter um código de barras")
    private String codigoDeBarras;

    @NotBlank(message = "O item deve conter um nome")
    private String nome;

    private String descricao;
    private CategoriaDTO categoriaDTO;

    @NotBlank(message = "O item deve conter uma marca")
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
