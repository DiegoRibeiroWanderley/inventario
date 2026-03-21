package com.inventario.projeto.service;

import com.inventario.projeto.payload.DTO.ItemDTO;
import com.inventario.projeto.payload.Response;
import com.inventario.projeto.payload.ResponseSistemaABC;

public interface ItemService {

    Response<ItemDTO> findAll(String palavraChave, String categoria, Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarItemsPor, String ordem);

    Response<ItemDTO> findItemsEmAlerta(Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarItemsPor, String ordem);

    ResponseSistemaABC findItemsSistemaABC(Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarItemsPor, String ordem);

    ItemDTO addItem(ItemDTO itemDTO, Integer categoriaId);

    ItemDTO updateItem(Integer id, ItemDTO itemDTO);

    ItemDTO deleteItem(Integer id);
}
