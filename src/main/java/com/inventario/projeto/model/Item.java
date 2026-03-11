package com.inventario.projeto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "O item deve conter um nome")
    private String nome;

    @NotEmpty(message = "O item deve conter um SKU")
    private String SKU;

    @NotEmpty(message = "O item deve conter um código de barras")
    private String codigoDeBarras;

    private String descricao;
    private Integer quantidade;
}