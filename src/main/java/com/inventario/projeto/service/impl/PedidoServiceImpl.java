package com.inventario.projeto.service.impl;

import com.inventario.projeto.exception.NotFoundException;
import com.inventario.projeto.mapper.PedidoMapper;
import com.inventario.projeto.model.Item;
import com.inventario.projeto.model.ItemPedido;
import com.inventario.projeto.model.Pedido;
import com.inventario.projeto.model.enums.Status;
import com.inventario.projeto.payload.DTO.PedidoDTO;
import com.inventario.projeto.repositories.ItemRepository;
import com.inventario.projeto.repositories.PedidoRepository;
import com.inventario.projeto.service.PedidoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemRepository itemRepository;
    private final PedidoMapper pedidoMapper;

    @Transactional
    @Override
    public PedidoDTO adicionarItemAoPedido(Integer itemId, Integer quantidade) {
        Pedido pedido = criarPedido();

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item", itemId));

        ItemPedido novoItem = new ItemPedido();
        novoItem.setItem(item);
        novoItem.setPrecoDoItem(item.getPrecoVenda());
        novoItem.setTaxaItem(item.getTaxa());
        novoItem.setQuantidade(quantidade);

        novoItem.setPedido(pedido);
        pedido.getItens().add(novoItem);

        double precoItem = novoItem.getPrecoDoItem() * quantidade;
        double precoTaxa = novoItem.getPrecoDoItem() * quantidade * novoItem.getTaxaItem() / 100;
        pedido.setPrecoItems(pedido.getPrecoItems() + precoItem);
        pedido.setPrecoTaxas(pedido.getPrecoTaxas() + precoTaxa);
        pedido.setPrecoTotal(pedido.getPrecoItems() + pedido.getPrecoTaxas());

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return pedidoMapper.toPedidoDTO(pedidoSalvo);
    }

    @Transactional
    @Override
    public PedidoDTO lancarPedido() {
        Pedido pedido = pedidoRepository.findPedidoByStatus(Status.EM_CRIACAO);
        pedido.setStatus(Status.LANCADO);

        Pedido pedidoLancado =  pedidoRepository.save(pedido);
        return pedidoMapper.toPedidoDTO(pedidoLancado);
    }

    private Pedido criarPedido() {
        if (pedidoRepository.findPedidoByStatus(Status.EM_CRIACAO) == null) {
            Pedido pedido = new Pedido();
            pedido.setStatus(Status.EM_CRIACAO);
            pedido.setDataPedido(LocalDate.now());
            pedido.setItens(new ArrayList<>());
            pedido.setPrecoItems(0.0);
            pedido.setPrecoTaxas(0.0);
            pedido.setPrecoTotal(0.0);
            pedido.setStatus(Status.EM_CRIACAO);

            return pedido;
        } else {
            return pedidoRepository.findPedidoByStatus(Status.EM_CRIACAO);
        }
    }
}
