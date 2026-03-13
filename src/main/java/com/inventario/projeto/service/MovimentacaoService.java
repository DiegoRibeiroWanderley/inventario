package com.inventario.projeto.service;

import com.inventario.projeto.DTOs.MovimentacaoDTO;

import java.util.List;

public interface MovimentacaoService {

    List<MovimentacaoDTO> todasMovimentacoes();

    List<MovimentacaoDTO> buscarMovimentacoesPorItem(Integer id);

    MovimentacaoDTO movimentacao(String tipo, Integer itemId, Integer quantidade);
}