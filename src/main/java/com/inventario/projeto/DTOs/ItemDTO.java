package com.inventario.projeto.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private Integer id;
    private String nome;
    private String SKU;
    private String codigoDeBarras;
    private String descricao;
    private Integer quantidade;
}
