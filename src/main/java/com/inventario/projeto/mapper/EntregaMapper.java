package com.inventario.projeto.mapper;

import com.inventario.projeto.model.Entrega;
import com.inventario.projeto.payload.DTO.EntregaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EntregaMapper {

    @Mapping(target = "movimentacaoId", ignore = true)
    EntregaDTO toEntregaDTO(Entrega entrega);

    @Mapping(target = "movimentacao", ignore = true)
    Entrega toEntrega(EntregaDTO entregaDTO);

    List<EntregaDTO> toEntregaDTOs(List<Entrega> entregas);
}
