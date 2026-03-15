package com.inventario.projeto.service.impl;

import com.inventario.projeto.DTOs.MovimentacaoDTO;
import com.inventario.projeto.exception.APIException;
import com.inventario.projeto.exception.NotFoundException;
import com.inventario.projeto.mapper.MovimentacaoMapper;
import com.inventario.projeto.model.Item;
import com.inventario.projeto.model.Movimentacao;
import com.inventario.projeto.model.enums.Meses;
import com.inventario.projeto.repositories.ItemRepository;
import com.inventario.projeto.repositories.MovimentacaoRepository;
import com.inventario.projeto.service.MovimentacaoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimentacaoServiceImpl implements MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ItemRepository itemRepository;

    private final MovimentacaoMapper movimentacaoMapper;

    @Override
    public List<MovimentacaoDTO> todasMovimentacoes() {
        List<Movimentacao> movimentacoes = movimentacaoRepository.findAll();
        return movimentacaoMapper.toMovimentacaoDTOs(movimentacoes);
    }

    @Override
    public List<MovimentacaoDTO> buscarMovimentacoesPorItem(Integer id) {
        List<Movimentacao> movimentacoesPorItem = movimentacaoRepository.findByItemId(id);
        return movimentacaoMapper.toMovimentacaoDTOs(movimentacoesPorItem);
    }

    @Override
    public List<MovimentacaoDTO> buscarMovimentacaoPorMesAno(String mes, Integer ano) {
        Meses mesEnum = Meses.valueOf(mes.toUpperCase());
        int mesInt = mesEnum.ordinal() + 1;

        LocalDate dataInicio = LocalDate.of(ano, mesInt, 1);
        LocalDate dataFinal = dataInicio.withDayOfMonth(dataInicio.lengthOfMonth());

        List<Movimentacao> movimentacoesPorMesAno = movimentacaoRepository.findMovimentacaoByDataMovimentacaoBetween(dataInicio, dataFinal);

        return movimentacaoMapper.toMovimentacaoDTOs(movimentacoesPorMesAno);
    }

    @Transactional
    @Override
    public MovimentacaoDTO movimentacao(String tipo, Integer itemId, Integer quantidade) {
        Item itemFromDB = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item", itemId));

        int qtde = tipo.equalsIgnoreCase("entrada") ? quantidade : -quantidade;

        itemFromDB.setQuantidadeEmEstoque(itemFromDB.getQuantidadeEmEstoque() + qtde);
        itemFromDB.setUltimoUpdate(LocalDate.now());

        if (itemFromDB.getQuantidadeEmEstoque() < 0) {
            throw new APIException("Não há estoque sufuciente para essa movimentação");
        }

        itemRepository.save(itemFromDB);

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setItem(itemFromDB);
        movimentacao.setQuantidade(qtde);

        movimentacao = movimentacaoRepository.save(movimentacao);
        return movimentacaoMapper.toMovimentacaoDTO(movimentacao);
    }

    @Transactional
    @Override
    public MovimentacaoDTO updateMovimentacao(MovimentacaoDTO movimentacaoDTO, Integer id) {
        Movimentacao movimentacaoFromDB = movimentacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movimentacao", id));

        Movimentacao movimentacaoToBeUpdated = movimentacaoMapper.toMovimentacao(movimentacaoDTO);

        if (movimentacaoToBeUpdated.getQuantidade() == null){

            movimentacaoToBeUpdated.setQuantidade(movimentacaoFromDB.getQuantidade());
        } else {
            Item itemFromMovimentacao = itemRepository.findById(movimentacaoFromDB.getItem().getId())
                    .orElseThrow(() -> new NotFoundException("Item", movimentacaoFromDB.getItem().getId()));

            itemFromMovimentacao.setQuantidadeEmEstoque(
                    itemFromMovimentacao.getQuantidadeEmEstoque()
                            - movimentacaoFromDB.getQuantidade()
                            + movimentacaoToBeUpdated.getQuantidade()
            );

            itemRepository.save(itemFromMovimentacao);
        }

        if (movimentacaoToBeUpdated.getReponsavel() == null) movimentacaoToBeUpdated.setReponsavel(movimentacaoFromDB.getReponsavel());
        movimentacaoToBeUpdated.setDataMovimentacao(LocalDate.now());
        movimentacaoToBeUpdated.setItem(movimentacaoFromDB.getItem());
        movimentacaoToBeUpdated.setId(movimentacaoFromDB.getId());

        Movimentacao movimentacaoUpdated = movimentacaoRepository.save(movimentacaoToBeUpdated);
        return movimentacaoMapper.toMovimentacaoDTO(movimentacaoUpdated);
    }

    @Transactional
    @Override
    public MovimentacaoDTO deletarMovimentacao(Integer id) {
        Movimentacao movimentacaoFromDB = movimentacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movimentacao", id));

        Item itemFromMovimentacao = itemRepository.findById(movimentacaoFromDB.getItem().getId())
                .orElseThrow(() -> new NotFoundException("Item", movimentacaoFromDB.getItem().getId()));

        itemFromMovimentacao.setQuantidadeEmEstoque(itemFromMovimentacao.getQuantidadeEmEstoque() - movimentacaoFromDB.getQuantidade());
        itemRepository.save(itemFromMovimentacao);

        movimentacaoRepository.delete(movimentacaoFromDB);
        return movimentacaoMapper.toMovimentacaoDTO(movimentacaoFromDB);
    }
}