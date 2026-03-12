package com.inventario.projeto.service;

import com.inventario.projeto.DTOs.ItemDTO;

import java.util.List;

public interface ItemService {

    List<ItemDTO> findAll();

    ItemDTO addItem(ItemDTO itemDTO);

    ItemDTO updateItem(Integer id, ItemDTO itemDTO);

    ItemDTO deleteItem(Integer id);
}
