package com.inventario.projeto.controller;

import com.inventario.projeto.model.enums.ParametrosDeBusca;
import com.inventario.projeto.payload.DTO.ItemDTO;
import com.inventario.projeto.payload.Response;
import com.inventario.projeto.payload.ResponseSistemaABC;
import com.inventario.projeto.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/itens")
    public ResponseEntity<Response<ItemDTO>> getItems(
            @RequestParam(name = "palavraChave", required = false) String palavraChave,
            @RequestParam(name = "categoria", required = false) String categoria,
            @RequestParam(name = "numeroDaPagina",
                    defaultValue = ParametrosDeBusca.NUMERO_DA_PAGINA, required = false) Integer numeroDaPagina,
            @RequestParam(name = "tamanhoDaPagina",
                    defaultValue = ParametrosDeBusca.TAMANHO_DA_PAGINA, required = false) Integer tamanhoDaPagina,
            @RequestParam(name = "ordenarItemsPor",
                    defaultValue = ParametrosDeBusca.ORDENAR_ITEMS_POR, required = false) String ordenarItemsPor,
            @RequestParam(name = "ordem",
                    defaultValue = ParametrosDeBusca.ORDEM, required = false) String ordem) {
        Response<ItemDTO> items = itemService.findAll(palavraChave, categoria, numeroDaPagina, tamanhoDaPagina, ordenarItemsPor, ordem);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/itens/alerta")
    public ResponseEntity<Response<ItemDTO>> getItemsEmAlerta(
            @RequestParam(name = "numeroDaPagina",
                    defaultValue = ParametrosDeBusca.NUMERO_DA_PAGINA, required = false) Integer numeroDaPagina,
            @RequestParam(name = "tamanhoDaPagina",
                    defaultValue = ParametrosDeBusca.TAMANHO_DA_PAGINA, required = false) Integer tamanhoDaPagina,
            @RequestParam(name = "ordenarItemsPor",
                    defaultValue = ParametrosDeBusca.ORDENAR_ITEMS_POR, required = false) String ordenarItemsPor,
            @RequestParam(name = "ordem",
                    defaultValue = ParametrosDeBusca.ORDEM, required = false) String ordem) {
        Response<ItemDTO> items = itemService.findItemsEmAlerta(numeroDaPagina, tamanhoDaPagina, ordenarItemsPor, ordem);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("itens/sistemaABC")
    public ResponseEntity<ResponseSistemaABC> getItemsSistemaABC(
            @RequestParam(name = "numeroDaPagina",
                    defaultValue = ParametrosDeBusca.NUMERO_DA_PAGINA, required = false) Integer numeroDaPagina,
            @RequestParam(name = "tamanhoDaPagina",
                    defaultValue = ParametrosDeBusca.TAMANHO_DA_PAGINA, required = false) Integer tamanhoDaPagina,
            @RequestParam(name = "ordenarItemsPor",
                    defaultValue = ParametrosDeBusca.ORDENAR_ITEMS_POR, required = false) String ordenarItemsPor,
            @RequestParam(name = "ordem",
                    defaultValue = ParametrosDeBusca.ORDEM, required = false) String ordem) {
        ResponseSistemaABC itemSistemaABC = itemService.findItemsSistemaABC(numeroDaPagina, tamanhoDaPagina, ordenarItemsPor, ordem);
        return new ResponseEntity<>(itemSistemaABC, HttpStatus.OK);
    }

    @PostMapping("/itens/add/categoria/{categoriaId}")
    public ResponseEntity<ItemDTO> addItem(@RequestBody ItemDTO itemDTO, @PathVariable Integer categoriaId) {
        ItemDTO addedItem = itemService.addItem(itemDTO, categoriaId);
        return new ResponseEntity<>(addedItem, HttpStatus.CREATED);
    }

    @PutMapping("/item/{id}/update")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Integer id, @RequestBody ItemDTO itemDTO) {
        ItemDTO updatedItem = itemService.updateItem(id, itemDTO);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @DeleteMapping("/item/{id}/delete")
    public ResponseEntity<ItemDTO> deleteItem(@PathVariable Integer id) {
        ItemDTO deletedItem = itemService.deleteItem(id);
        return new ResponseEntity<>(deletedItem, HttpStatus.OK);
    }
}