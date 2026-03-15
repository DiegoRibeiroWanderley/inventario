package com.inventario.projeto.mapper;

import com.inventario.projeto.payload.CategoriaDTO;
import com.inventario.projeto.model.Categoria;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    CategoriaDTO toCategoriaDTO(Categoria categoria);
    Categoria toCategoria(CategoriaDTO categoriaDTO);
    List<CategoriaDTO> toCategoriaDTOs(List<Categoria> categorias);
}
