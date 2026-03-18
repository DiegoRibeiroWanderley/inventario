package com.inventario.projeto.service.impl;

import com.inventario.projeto.exception.NotFoundException;
import com.inventario.projeto.mapper.EntregaMapper;
import com.inventario.projeto.mapper.MovimentacaoMapper;
import com.inventario.projeto.model.Entrega;
import com.inventario.projeto.model.Movimentacao;
import com.inventario.projeto.model.enums.Meses;
import com.inventario.projeto.model.enums.Status;
import com.inventario.projeto.payload.DTO.EntregaDTO;
import com.inventario.projeto.payload.DTO.MovimentacaoDTO;
import com.inventario.projeto.payload.Response;
import com.inventario.projeto.repositories.EntregaRepository;
import com.inventario.projeto.repositories.MovimentacaoRepository;
import com.inventario.projeto.service.EntregaService;
import com.inventario.projeto.service.MovimentacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntregaServiceImpl implements EntregaService {

    private final EntregaRepository entregaRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    private final EntregaMapper entregaMapper;
    private final MovimentacaoService movimentacaoService;
    private final MovimentacaoMapper movimentacaoMapper;


    @Override
    public Response<EntregaDTO> findAll(Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarEntradasPor, String ordem) {
        Sort sort = ordem.equalsIgnoreCase("asc")
                ? Sort.by(ordenarEntradasPor).ascending()
                : Sort.by(ordenarEntradasPor).descending();

        Pageable paginacao = PageRequest.of(numeroDaPagina, tamanhoDaPagina, sort);
        Page<Entrega> pagina = entregaRepository.findAll(paginacao);

        return Response.<EntregaDTO>builder()
                .content(toEntregaDTOs(pagina))
                .numeroDaPagina(pagina.getNumber())
                .tamanhoDaPagina(pagina.getSize())
                .totalDeElementos(pagina.getTotalElements())
                .totalDePaginas(pagina.getTotalPages())
                .ultimaPagina(pagina.isLast())
                .build();
    }

    @Override
    public Response<EntregaDTO> findByMovimentacao(Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarEntradasPor, String ordem, Integer movimentacaoId) {
        Sort sort = ordem.equalsIgnoreCase("asc")
                ? Sort.by(ordenarEntradasPor).ascending()
                : Sort.by(ordenarEntradasPor).descending();

        Pageable paginacao = PageRequest.of(numeroDaPagina, tamanhoDaPagina, sort);
        Page<Entrega> pagina = entregaRepository.findEntregaByMovimentacao_Id(movimentacaoId ,paginacao);

        return Response.<EntregaDTO>builder()
                .content(toEntregaDTOs(pagina))
                .numeroDaPagina(pagina.getNumber())
                .tamanhoDaPagina(pagina.getSize())
                .totalDeElementos(pagina.getTotalElements())
                .totalDePaginas(pagina.getTotalPages())
                .ultimaPagina(pagina.isLast())
                .build();
    }

    @Override
    public Response<EntregaDTO> findByItem(Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarEntradasPor, String ordem, Integer itemId) {
        Sort sort = ordem.equalsIgnoreCase("asc")
                ? Sort.by(ordenarEntradasPor).ascending()
                : Sort.by(ordenarEntradasPor).descending();

        Pageable paginacao = PageRequest.of(numeroDaPagina, tamanhoDaPagina, sort);
        Page<Entrega> pagina = entregaRepository.findEntregaByMovimentacao_Item_Id(itemId ,paginacao);

        return Response.<EntregaDTO>builder()
                .content(toEntregaDTOs(pagina))
                .numeroDaPagina(pagina.getNumber())
                .tamanhoDaPagina(pagina.getSize())
                .totalDeElementos(pagina.getTotalElements())
                .totalDePaginas(pagina.getTotalPages())
                .ultimaPagina(pagina.isLast())
                .build();
    }

    @Override
    public Response<EntregaDTO> findByPeriodo(Integer numeroDaPagina, Integer tamanhoDaPagina, String ordenarEntradasPor, String ordem, String mesInicio, Integer anoInicio, String mesFim, Integer anoFim) {
        Sort sort = ordem.equalsIgnoreCase("asc")
                ? Sort.by(ordenarEntradasPor).ascending()
                : Sort.by(ordenarEntradasPor).descending();

        Meses mesInicioEnum = Meses.valueOf(mesInicio.toUpperCase());
        Meses mesFimEnum = Meses.valueOf(mesFim.toUpperCase());
        int mesInicioInt = mesInicioEnum.ordinal() + 1;
        int mesFimInt = mesFimEnum.ordinal() + 1;
        YearMonth ultimoMes = YearMonth.of(anoFim, mesFimInt);

        LocalDate inicio = LocalDate.of(anoInicio, mesInicioInt, 1);
        LocalDate fim = LocalDate.of(anoFim, mesFimInt, ultimoMes.lengthOfMonth());

        Pageable paginacao = PageRequest.of(numeroDaPagina, tamanhoDaPagina, sort);
        Page<Entrega> pagina = entregaRepository.findEntregaByPeriodo(inicio, fim, paginacao);

        return Response.<EntregaDTO>builder()
                .content(toEntregaDTOs(pagina))
                .numeroDaPagina(pagina.getNumber())
                .tamanhoDaPagina(pagina.getSize())
                .totalDeElementos(pagina.getTotalElements())
                .totalDePaginas(pagina.getTotalPages())
                .ultimaPagina(pagina.isLast())
                .build();
    }

    @Override
    public EntregaDTO createEntrega(EntregaDTO entregaDTO, Integer itemId, Integer quantidade) {
        Entrega entrega = new Entrega();

        MovimentacaoDTO movimentacaoDTO = movimentacaoService.movimentacao("saida", itemId, quantidade);
        Movimentacao movimentacao = movimentacaoMapper.toMovimentacao(movimentacaoDTO);

        entrega.setMovimentacao(movimentacao);
        entrega.setStatus(Status.PEDIDO_RECEBIDO);
        entrega.setTransportadora(entregaDTO.getTransportadora());
        entrega.setValorFrete(entregaDTO.getValorFrete());

        Entrega savedEntrega = entregaRepository.save(entrega);
        EntregaDTO savedEntregaDTO = entregaMapper.toEntregaDTO(savedEntrega);
        savedEntregaDTO.setMovimentacaoId(movimentacao.getId());

        return savedEntregaDTO;
    }

    @Override
    public EntregaDTO updateEntrega(EntregaDTO entregaDTO, Integer id) {
        Entrega entregaFromDB = entregaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entrega", id));

        Entrega entregaToBeUpdated = entregaMapper.toEntrega(entregaDTO);

        if (entregaToBeUpdated.getMovimentacao() == null) entregaToBeUpdated.setMovimentacao(entregaFromDB.getMovimentacao());
        if (entregaToBeUpdated.getDataDespacho() == null) entregaToBeUpdated.setDataDespacho(entregaFromDB.getDataDespacho());
        if (entregaToBeUpdated.getValorFrete() == null) entregaToBeUpdated.setValorFrete(entregaFromDB.getValorFrete());
        if (entregaToBeUpdated.getStatus() == null) entregaToBeUpdated.setStatus(entregaFromDB.getStatus());
        if (entregaToBeUpdated.getTransportadora() == null) entregaToBeUpdated.setTransportadora(entregaFromDB.getTransportadora());
        entregaToBeUpdated.setEntregaId(entregaFromDB.getEntregaId());

        Entrega updatedEntrega = entregaRepository.save(entregaToBeUpdated);
        EntregaDTO updatedEntregaDTO = entregaMapper.toEntregaDTO(updatedEntrega);
        updatedEntregaDTO.setMovimentacaoId(entregaFromDB.getMovimentacao().getId());

        return updatedEntregaDTO;
    }

    @Override
    public EntregaDTO deleteEntrega(Integer id) {
        Entrega entregaFromDB = entregaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entrega", id));
        Movimentacao movimentacao = movimentacaoRepository.findById(entregaFromDB.getMovimentacao().getId())
                .orElseThrow(() -> new NotFoundException("Movimentacao", id));

        entregaRepository.deleteById(id);
        movimentacaoService.deletarMovimentacao(movimentacao.getId());

        EntregaDTO entregaDTO = entregaMapper.toEntregaDTO(entregaFromDB);
        entregaDTO.setMovimentacaoId(movimentacao.getId());

        return entregaDTO;
    }

    private List<EntregaDTO> toEntregaDTOs(Page<Entrega> pagina) {
        List<Entrega> entregas = pagina.getContent();
        return entregas.stream().map(entrega -> {
            EntregaDTO entregaDTO = entregaMapper.toEntregaDTO(entrega);
            entregaDTO.setMovimentacaoId(entrega.getMovimentacao().getId());
            return entregaDTO;
        }).toList();
    }
}