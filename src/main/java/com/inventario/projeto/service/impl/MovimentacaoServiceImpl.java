package com.inventario.projeto.service.impl;

import com.inventario.projeto.payload.DTO.MovimentacaoDTO;
import com.inventario.projeto.exception.APIException;
import com.inventario.projeto.exception.NotFoundException;
import com.inventario.projeto.mapper.MovimentacaoMapper;
import com.inventario.projeto.model.Item;
import com.inventario.projeto.model.Movimentacao;
import com.inventario.projeto.model.enums.Meses;
import com.inventario.projeto.payload.Response;
import com.inventario.projeto.repositories.ItemRepository;
import com.inventario.projeto.repositories.MovimentacaoRepository;
import com.inventario.projeto.service.MovimentacaoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Response<MovimentacaoDTO> todasMovimentacoes(Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarMovimentacoesPor, String ordem) {

        Sort sort = ordem.equalsIgnoreCase("asc")
                ? Sort.by(ordenarMovimentacoesPor).ascending()
                : Sort.by(ordenarMovimentacoesPor).descending();

        Pageable pageable = PageRequest.of(numeroDaPagina, tamanhoDaPagina, sort);
        Page<Movimentacao> pagina = movimentacaoRepository.findAll(pageable);
        List<Movimentacao> movimentacoes = pagina.getContent();

        return Response.<MovimentacaoDTO>builder()
                .content(movimentacaoMapper.toMovimentacaoDTOs(movimentacoes))
                .numeroDaPagina(pagina.getNumber())
                .tamanhoDaPagina(pagina.getSize())
                .totalDeElementos(pagina.getTotalElements())
                .totalDePaginas(pagina.getTotalPages())
                .ultimaPagina(pagina.isLast())
                .build();
    }

    @Override
    public Response<MovimentacaoDTO> buscarMovimentacoesPorItem(Integer id, Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarMovimentacoesPor, String ordem) {

        Sort sort = ordem.equalsIgnoreCase("asc")
                ? Sort.by(ordenarMovimentacoesPor).ascending()
                : Sort.by(ordenarMovimentacoesPor).descending();

        Pageable pageable = PageRequest.of(numeroDaPagina, tamanhoDaPagina, sort);
        Page<Movimentacao> pagina = movimentacaoRepository.findByItemId(id, pageable);
        List<Movimentacao> movimentacoesPorItem = pagina.getContent();

        return Response.<MovimentacaoDTO>builder()
                .content(movimentacaoMapper.toMovimentacaoDTOs(movimentacoesPorItem))
                .numeroDaPagina(pagina.getNumber())
                .tamanhoDaPagina(pagina.getSize())
                .totalDeElementos(pagina.getTotalElements())
                .totalDePaginas(pagina.getTotalPages())
                .ultimaPagina(pagina.isLast())
                .build();
    }

    @Override
    public Response<MovimentacaoDTO> buscarMovimentacaoPorMesAno(String mes, Integer ano, Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarMovimentacoesPor, String ordem) {
        Meses mesEnum = Meses.valueOf(mes.toUpperCase());
        int mesInt = mesEnum.ordinal() + 1;

        LocalDate dataInicio = LocalDate.of(ano, mesInt, 1);
        LocalDate dataFinal = dataInicio.withDayOfMonth(dataInicio.lengthOfMonth());

        Sort sort = ordem.equalsIgnoreCase("asc")
                ? Sort.by(ordenarMovimentacoesPor).ascending()
                : Sort.by(ordenarMovimentacoesPor).descending();

        Pageable pageable = PageRequest.of(numeroDaPagina, tamanhoDaPagina, sort);
        Page<Movimentacao> pagina = movimentacaoRepository.findMovimentacaoByDataMovimentacaoBetween(dataInicio, dataFinal, pageable);
        List<Movimentacao> movimentacoesPorMesAno = pagina.getContent();

        return Response.<MovimentacaoDTO>builder()
                .content(movimentacaoMapper.toMovimentacaoDTOs(movimentacoesPorMesAno))
                .numeroDaPagina(pagina.getNumber())
                .tamanhoDaPagina(pagina.getSize())
                .totalDeElementos(pagina.getTotalElements())
                .totalDePaginas(pagina.getTotalPages())
                .ultimaPagina(pagina.isLast())
                .build();
    }

    @Transactional
    @Override
    public MovimentacaoDTO movimentacao(String tipo, Integer itemId, Integer quantidade) {
        Item itemFromDB = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Item", itemId));

        int qtde = tipo.equalsIgnoreCase("entrada") ? quantidade : -quantidade;

        itemFromDB.setQuantidadeEmEstoque(itemFromDB.getQuantidadeEmEstoque() + qtde);
        itemFromDB.setUltimoUpdate(LocalDate.now());
        if (tipo.equalsIgnoreCase("saida")) itemFromDB.setSaidas(itemFromDB.getSaidas() + quantidade);
        if (tipo.equalsIgnoreCase("entrada")) itemFromDB.setEntradas(itemFromDB.getEntradas() + quantidade);

        double precoVenda = itemFromDB.getPrecoVenda() - itemFromDB.getPrecoCompra();
        double precoVendaComTaxa = precoVenda + precoVenda * (itemFromDB.getTaxa() / 100);

        itemFromDB.setValorGanho(itemFromDB.getSaidas() * precoVendaComTaxa);

        if (itemFromDB.getQuantidadeEmEstoque() < 0) {
            throw new APIException("Não há estoque sufuciente para essa movimentação");
        }

        itemRepository.save(itemFromDB);

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setItem(itemFromDB);
        movimentacao.setQuantidade(quantidade);
        movimentacao.setTipo(tipo);

        movimentacao = movimentacaoRepository.save(movimentacao);
        return movimentacaoMapper.toMovimentacaoDTO(movimentacao);
    }

    @Transactional
    @Override
    public MovimentacaoDTO updateMovimentacao(MovimentacaoDTO movimentacaoDTO, Integer id) {
        Movimentacao movimentacaoFromDB = movimentacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movimentacao", id));

        Movimentacao movimentacaoToBeUpdated = movimentacaoMapper.toMovimentacao(movimentacaoDTO);

        if (movimentacaoToBeUpdated.getTipo() == null) movimentacaoToBeUpdated.setTipo(movimentacaoFromDB.getTipo());

        if (movimentacaoToBeUpdated.getQuantidade() == null){

            movimentacaoToBeUpdated.setQuantidade(movimentacaoFromDB.getQuantidade());
        } else {
            Item itemFromMovimentacao = itemRepository.findById(movimentacaoFromDB.getItem().getId())
                    .orElseThrow(() -> new NotFoundException("Item", movimentacaoFromDB.getItem().getId()));

            if (movimentacaoToBeUpdated.getTipo().equals(movimentacaoFromDB.getTipo())) {
                if (movimentacaoToBeUpdated.getTipo().equalsIgnoreCase("entrada")) {
                    itemFromMovimentacao.setEntradas(itemFromMovimentacao.getEntradas() - movimentacaoFromDB.getQuantidade() + movimentacaoToBeUpdated.getQuantidade());
                }
                if (movimentacaoToBeUpdated.getTipo().equalsIgnoreCase("saida")) {
                    itemFromMovimentacao.setSaidas(itemFromMovimentacao.getSaidas() - movimentacaoFromDB.getQuantidade() + movimentacaoToBeUpdated.getQuantidade());
                }
            } else {
                if (movimentacaoToBeUpdated.getTipo().equalsIgnoreCase("entrada")) {
                    itemFromMovimentacao.setSaidas(itemFromMovimentacao.getSaidas() - movimentacaoFromDB.getQuantidade());
                    itemFromMovimentacao.setEntradas(itemFromMovimentacao.getEntradas() + movimentacaoToBeUpdated.getQuantidade());
                }
                if (movimentacaoToBeUpdated.getTipo().equalsIgnoreCase("saida")) {
                    itemFromMovimentacao.setEntradas(itemFromMovimentacao.getEntradas() - movimentacaoFromDB.getQuantidade());
                    itemFromMovimentacao.setSaidas(itemFromMovimentacao.getSaidas() + movimentacaoToBeUpdated.getQuantidade());
                }
            }

            itemFromMovimentacao.setQuantidadeEmEstoque(
                    itemFromMovimentacao.getQuantidadeEmEstoque()
                            - (movimentacaoFromDB.getTipo().equalsIgnoreCase("entrada") ? movimentacaoFromDB.getQuantidade() : -movimentacaoFromDB.getQuantidade())
                            + (movimentacaoToBeUpdated.getTipo().equalsIgnoreCase("entrada") ? movimentacaoToBeUpdated.getQuantidade() : -movimentacaoToBeUpdated.getQuantidade())
            );

            double precoVenda = itemFromMovimentacao.getPrecoVenda() - itemFromMovimentacao.getPrecoCompra();
            double precoComTaxa = (precoVenda + (precoVenda * itemFromMovimentacao.getTaxa()/100)) * itemFromMovimentacao.getSaidas();
            itemFromMovimentacao.setValorGanho(precoComTaxa);

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
        if (movimentacaoFromDB.getTipo().equalsIgnoreCase("entrada")) {
            itemFromMovimentacao.setEntradas(itemFromMovimentacao.getEntradas()  - movimentacaoFromDB.getQuantidade());
        } else {
            itemFromMovimentacao.setSaidas(itemFromMovimentacao.getSaidas() - movimentacaoFromDB.getQuantidade());
        }

        itemRepository.save(itemFromMovimentacao);

        movimentacaoRepository.delete(movimentacaoFromDB);
        return movimentacaoMapper.toMovimentacaoDTO(movimentacaoFromDB);
    }
}