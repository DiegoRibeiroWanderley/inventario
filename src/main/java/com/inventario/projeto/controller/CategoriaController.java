package com.inventario.projeto.controller;

import com.inventario.projeto.DTOs.CategoriaDTO;
import com.inventario.projeto.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping("/items")
    public ResponseEntity<List<CategoriaDTO>> getCategorias() {
        List<CategoriaDTO> categorias = categoriaService.findAll();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @PostMapping("/items/add")
    public ResponseEntity<CategoriaDTO> addCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO addedCategoria = categoriaService.addCategoria(categoriaDTO);
        return new ResponseEntity<>(addedCategoria, HttpStatus.CREATED);
    }

    @PutMapping("/items/{id}/update")
    public ResponseEntity<CategoriaDTO> updateItem(@PathVariable Integer id, @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO updatedCategoria = categoriaService.updateCategoria(id, categoriaDTO);
        return new ResponseEntity<>(updatedCategoria, HttpStatus.OK);
    }

    @DeleteMapping("/items/{id}/delete")
    public ResponseEntity<CategoriaDTO> deleteItem(@PathVariable Integer id) {
        CategoriaDTO deletedCategoria = categoriaService.deleteCategoria(id);
        return new ResponseEntity<>(deletedCategoria, HttpStatus.OK);
    }
}
