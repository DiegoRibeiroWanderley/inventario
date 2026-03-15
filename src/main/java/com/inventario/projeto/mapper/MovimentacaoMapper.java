package com.inventario.projeto.mapper;

import com.inventario.projeto.payload.MovimentacaoDTO;
import com.inventario.projeto.model.Movimentacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ItemMapper.class})
public interface MovimentacaoMapper {

    @Mapping(target = "itemDTO", source = "item")
    MovimentacaoDTO toMovimentacaoDTO(Movimentacao movimentacao);
    List<MovimentacaoDTO> toMovimentacaoDTOs(List<Movimentacao> movimentacoes);

    @Mapping(target = "item", source = "itemDTO")
    Movimentacao toMovimentacao(MovimentacaoDTO movimentacaoDTO);
}
