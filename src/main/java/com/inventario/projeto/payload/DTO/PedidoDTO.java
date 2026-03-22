package com.inventario.projeto.payload.DTO;

import com.inventario.projeto.model.ItemPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long id;
    List<ItemPedido> itens;
    private Double precoItems;
    private Double precoTaxas;
    private Double precoTotal;
    private LocalDate dataPedido;
    private String status;
}
