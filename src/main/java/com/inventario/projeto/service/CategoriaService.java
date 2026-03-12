package com.inventario.projeto.service;

import com.inventario.projeto.DTOs.CategoriaDTO;

import java.util.List;

public interface CategoriaService {

    List<CategoriaDTO> findAll();

    CategoriaDTO addCategoria(CategoriaDTO categoriaDTO);

    CategoriaDTO updateCategoria(Integer id, CategoriaDTO categoriaDTO);

    CategoriaDTO deleteCategoria(Integer id);
}
