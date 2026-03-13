package com.inventario.projeto.service.impl;

import com.inventario.projeto.DTOs.MovimentacaoDTO;
import com.inventario.projeto.exception.APIException;
import com.inventario.projeto.exception.NotFoundException;
import com.inventario.projeto.mapper.MovimentacaoMapper;
import com.inventario.projeto.model.Item;
import com.inventario.projeto.model.Movimentacao;
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
}