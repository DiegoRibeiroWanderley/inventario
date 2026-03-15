package com.inventario.projeto.service;

import com.inventario.projeto.payload.ItemDTO;
import com.inventario.projeto.payload.ItemResponse;

import java.util.List;

public interface ItemService {

    ItemResponse findAll(Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarItemsPor, String ordem);

    ItemDTO addItem(ItemDTO itemDTO, Integer categoriaId);

    ItemDTO updateItem(Integer id, ItemDTO itemDTO);

    ItemDTO deleteItem(Integer id);
}
