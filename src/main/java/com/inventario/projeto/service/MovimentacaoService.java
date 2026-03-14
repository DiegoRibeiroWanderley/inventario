package com.inventario.projeto.service;

import com.inventario.projeto.DTOs.MovimentacaoDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface MovimentacaoService {

    List<MovimentacaoDTO> todasMovimentacoes();

    List<MovimentacaoDTO> buscarMovimentacoesPorItem(Integer id);

    List<MovimentacaoDTO> buscarMovimentacaoPorMesAno(String mes, Integer ano);

    MovimentacaoDTO movimentacao(String tipo, Integer itemId, Integer quantidade);
}