package com.inventario.projeto.service;

import com.inventario.projeto.payload.MovimentacaoDTO;
import com.inventario.projeto.payload.Response;

import java.util.List;

public interface MovimentacaoService {

    Response<MovimentacaoDTO> todasMovimentacoes(Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarMovimentacoesPor, String ordem);

    Response<MovimentacaoDTO> buscarMovimentacoesPorItem(Integer id, Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarMovimentacoesPor, String ordem);

    Response<MovimentacaoDTO> buscarMovimentacaoPorMesAno(String mes, Integer ano, Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarMovimentacoesPor, String ordem);

    MovimentacaoDTO movimentacao(String tipo, Integer itemId, Integer quantidade);

    MovimentacaoDTO updateMovimentacao(MovimentacaoDTO movimentacaoDTO, Integer id);

    MovimentacaoDTO deletarMovimentacao(Integer id);
}