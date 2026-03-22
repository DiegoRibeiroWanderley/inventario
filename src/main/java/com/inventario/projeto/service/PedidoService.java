package com.inventario.projeto.service;

import com.inventario.projeto.payload.DTO.PedidoDTO;

public interface PedidoService {

    PedidoDTO adicionarItemAoPedido(Integer itemId, Integer quantidade);

    PedidoDTO lancarPedido();
}
