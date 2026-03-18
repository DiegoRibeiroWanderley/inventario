package com.inventario.projeto.service.impl;

import com.inventario.projeto.mapper.EntregaMapper;
import com.inventario.projeto.model.Entrega;
import com.inventario.projeto.model.enums.Meses;
import com.inventario.projeto.payload.DTO.EntregaDTO;
import com.inventario.projeto.payload.Response;
import com.inventario.projeto.repositories.EntregaRepository;
import com.inventario.projeto.repositories.MovimentacaoRepository;
import com.inventario.projeto.service.EntregaService;
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
    public EntregaDTO createEntrega(EntregaDTO entregaDTO) {
        return null;
    }

    @Override
    public EntregaDTO updateEntrega(EntregaDTO entregaDTO, Integer id) {
        return null;
    }

    @Override
    public EntregaDTO deleteEntrega(Integer id) {
        return null;
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