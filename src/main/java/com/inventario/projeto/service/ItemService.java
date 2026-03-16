package com.inventario.projeto.service;

import com.inventario.projeto.payload.ItemDTO;
import com.inventario.projeto.payload.Response;

public interface ItemService {

    Response<ItemDTO> findAll(Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarItemsPor, String ordem);

    Response<ItemDTO> findItemsEmAlerta(Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarItemsPor, String ordem);

    ItemDTO addItem(ItemDTO itemDTO, Integer categoriaId);

    ItemDTO updateItem(Integer id, ItemDTO itemDTO);

    ItemDTO deleteItem(Integer id);
}
