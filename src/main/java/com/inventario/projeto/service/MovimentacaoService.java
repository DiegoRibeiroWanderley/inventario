package com.inventario.projeto.service;

import com.inventario.projeto.payload.DTO.MovimentacaoDTO;
import com.inventario.projeto.payload.Response;

public interface MovimentacaoService {

    Response<MovimentacaoDTO> todasMovimentacoes(Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarMovimentacoesPor, String ordem);

    Response<MovimentacaoDTO> buscarMovimentacoesPorItem(Integer id, Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarMovimentacoesPor, String ordem);

    Response<MovimentacaoDTO> buscarMovimentacaoPorPeriodo(String mesInicio, String mesFinal, Integer anoInicio, Integer anoFinal, Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarMovimentacoesPor, String ordem);

    MovimentacaoDTO movimentacao(String tipo, Integer itemId, Integer quantidade);

    MovimentacaoDTO updateMovimentacao(MovimentacaoDTO movimentacaoDTO, Integer id);

    MovimentacaoDTO deletarMovimentacao(Integer id);
}