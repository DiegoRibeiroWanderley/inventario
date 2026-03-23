package com.inventario.projeto.mapper;

import com.inventario.projeto.model.ItemPedido;
import com.inventario.projeto.payload.DTO.ItemPedidoDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    List<ItemPedidoDTO> toItensPedidoDTO(List<ItemPedido> itens);
}
