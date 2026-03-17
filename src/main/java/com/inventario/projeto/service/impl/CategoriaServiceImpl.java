package com.inventario.projeto.service.impl;

import com.inventario.projeto.payload.DTO.CategoriaDTO;
import com.inventario.projeto.exception.NotFoundException;
import com.inventario.projeto.mapper.CategoriaMapper;
import com.inventario.projeto.model.Categoria;
import com.inventario.projeto.repositories.CategoriaRepository;
import com.inventario.projeto.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    public List<CategoriaDTO> findAll() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categoriaMapper.toCategoriaDTOs(categorias);
    }

    @Override
    public CategoriaDTO addCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaMapper.toCategoria(categoriaDTO);
        categoria = categoriaRepository.save(categoria);
        return categoriaMapper.toCategoriaDTO(categoria);
    }

    @Override
    public CategoriaDTO updateCategoria(Integer id, CategoriaDTO categoriaDTO) {
        Categoria categoriaFromDB = categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria", id));
        Categoria categoriaToBeUpdated = categoriaMapper.toCategoria(categoriaDTO);

        categoriaToBeUpdated.setCategoriaId(categoriaFromDB.getCategoriaId());
        categoriaRepository.save(categoriaToBeUpdated);
        return categoriaMapper.toCategoriaDTO(categoriaFromDB);
    }

    @Override
    public CategoriaDTO deleteCategoria(Integer id) {
        Categoria categoriaFromDB = categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria", id));

        categoriaRepository.delete(categoriaFromDB);
        return categoriaMapper.toCategoriaDTO(categoriaFromDB);
    }
}
