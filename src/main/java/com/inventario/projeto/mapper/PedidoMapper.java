package com.inventario.projeto.mapper;

import com.inventario.projeto.model.Pedido;
import com.inventario.projeto.payload.DTO.PedidoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ItemMapper.class)
public interface PedidoMapper {

    PedidoDTO toPedidoDTO(Pedido pedido);
    Pedido toPedido(PedidoDTO pedidoDTO);
}
