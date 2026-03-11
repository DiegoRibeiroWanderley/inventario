package com.inventario.projeto.controller;

import com.inventario.projeto.DTOs.ItemDTO;
import com.inventario.projeto.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items")
    public ResponseEntity<List<ItemDTO>> getItems() {
        List<ItemDTO> items = itemService.findAll();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping("/items/add")
    public ResponseEntity<ItemDTO> addItem(@RequestBody ItemDTO itemDTO) {
        ItemDTO addedItem = itemService.addItem(itemDTO);
        return new ResponseEntity<>(addedItem, HttpStatus.CREATED);
    }

    @PutMapping("/items/{id}/update")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Integer id, @RequestBody ItemDTO itemDTO) {
        ItemDTO updatedItem = itemService.updateItem(id, itemDTO);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }
}