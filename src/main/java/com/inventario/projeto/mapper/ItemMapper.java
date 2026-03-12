package com.inventario.projeto.mapper;

import com.inventario.projeto.DTOs.ItemDTO;
import com.inventario.projeto.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface ItemMapper {

    @Mapping(target = "categoriaDTO", source = "categoria")
    ItemDTO toItemDTO(Item item);

    @Mapping(target = "categoria", source = "categoriaDTO")
    Item toItem(ItemDTO itemDTO);

    List<ItemDTO> toItemDTOs(List<Item> items);
}
