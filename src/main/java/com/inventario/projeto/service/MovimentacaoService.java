package com.inventario.projeto.service;

import com.inventario.projeto.payload.MovimentacaoDTO;

import java.util.List;

public interface MovimentacaoService {

    List<MovimentacaoDTO> todasMovimentacoes();

    List<MovimentacaoDTO> buscarMovimentacoesPorItem(Integer id);

    List<MovimentacaoDTO> buscarMovimentacaoPorMesAno(String mes, Integer ano);

    MovimentacaoDTO movimentacao(String tipo, Integer itemId, Integer quantidade);

    MovimentacaoDTO updateMovimentacao(MovimentacaoDTO movimentacaoDTO, Integer id);

    MovimentacaoDTO deletarMovimentacao(Integer id);
}