package com.inventario.projeto.payload.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDTO {

    private Long id;
    private Double taxaItem;
    private Integer quantidade;
    private Double precoDoItem;
}
