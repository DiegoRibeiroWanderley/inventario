package com.inventario.projeto.mapper;

import com.inventario.projeto.DTOs.ItemDTO;
import com.inventario.projeto.model.Item;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemDTO toItemDTO(Item item);
    Item toItem(ItemDTO itemDTO);
    List<ItemDTO> toItemDTOs(List<Item> items);
}
