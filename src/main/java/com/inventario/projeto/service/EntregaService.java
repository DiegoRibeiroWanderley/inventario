package com.inventario.projeto.service;

import com.inventario.projeto.payload.DTO.EntregaDTO;
import com.inventario.projeto.payload.Response;

public interface EntregaService {

    Response<EntregaDTO> findAll(Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarEntradasPor, String ordem);

    Response<EntregaDTO> findByPeriodo(Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarEntradasPor, String ordem, String mesInicio, Integer anoInicio, String mesFim, Integer anoFim);

    EntregaDTO createEntrega(EntregaDTO entregaDTO, Integer pedidoId);

    EntregaDTO updateEntrega(EntregaDTO entregaDTO, Integer id);

    EntregaDTO deleteEntrega(Integer id);
}
