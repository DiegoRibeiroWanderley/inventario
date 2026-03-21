package com.inventario.projeto.controller;

import com.inventario.projeto.model.enums.ParametrosDeBusca;
import com.inventario.projeto.payload.DTO.MovimentacaoDTO;
import com.inventario.projeto.payload.Response;
import com.inventario.projeto.service.MovimentacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    @GetMapping("/movimentacoes")
    public ResponseEntity<Response<MovimentacaoDTO>> todasMovimentacoes(
            @RequestParam(name = "numeroDaPagina",
                    defaultValue = ParametrosDeBusca.NUMERO_DA_PAGINA, required = false) Integer numeroDaPagina,
            @RequestParam(name = "tamanhoDaPagina",
                    defaultValue = ParametrosDeBusca.TAMANHO_DA_PAGINA, required = false) Integer tamanhoDaPagina,
            @RequestParam(name = "ordenarMovimentacoesPor",
                    defaultValue = ParametrosDeBusca.ORDENAR_MOVIMENTACOES_POR, required = false) String ordenarMovimentacoesPor,
            @RequestParam(name = "ordem",
                    defaultValue = ParametrosDeBusca.ORDEM, required = false) String ordem) {
        Response<MovimentacaoDTO> movimentacoes = movimentacaoService.todasMovimentacoes(numeroDaPagina, tamanhoDaPagina, ordenarMovimentacoesPor, ordem);
        return new ResponseEntity<>(movimentacoes, HttpStatus.OK);
    }

    @GetMapping("/movimentacoes/item/{id}")
    public ResponseEntity<Response<MovimentacaoDTO>> buscarMovimentacaoPorItem(
            @PathVariable Integer id,
            @RequestParam(name = "numeroDaPagina",
                    defaultValue = ParametrosDeBusca.NUMERO_DA_PAGINA, required = false) Integer numeroDaPagina,
            @RequestParam(name = "tamanhoDaPagina",
                    defaultValue = ParametrosDeBusca.TAMANHO_DA_PAGINA, required = false) Integer tamanhoDaPagina,
            @RequestParam(name = "ordenarMovimentacoesPor",
                    defaultValue = ParametrosDeBusca.ORDENAR_MOVIMENTACOES_POR, required = false) String ordenarMovimentacoesPor,
            @RequestParam(name = "ordem",
                    defaultValue = ParametrosDeBusca.ORDEM, required = false) String ordem) {
        Response<MovimentacaoDTO> movimentacoesDoItem = movimentacaoService.buscarMovimentacoesPorItem(
                id, numeroDaPagina, tamanhoDaPagina, ordenarMovimentacoesPor, ordem);
        return new ResponseEntity<>(movimentacoesDoItem, HttpStatus.OK);
    }

    @GetMapping("/movimentacoes/{mesInicio}/{mesFinal}/{anoInicio}/{anoFinal}")
    public ResponseEntity<Response<MovimentacaoDTO>> buscarMovimentacaoPorMesAno(
            @PathVariable String mesInicio,
            @PathVariable Integer anoInicio,
            @PathVariable String mesFinal,
            @PathVariable Integer anoFinal,
            @RequestParam(name = "numeroDaPagina",
                    defaultValue = ParametrosDeBusca.NUMERO_DA_PAGINA, required = false) Integer numeroDaPagina,
            @RequestParam(name = "tamanhoDaPagina",
                    defaultValue = ParametrosDeBusca.TAMANHO_DA_PAGINA, required = false) Integer tamanhoDaPagina,
            @RequestParam(name = "ordenarMovimentacoesPor",
                    defaultValue = ParametrosDeBusca.ORDENAR_MOVIMENTACOES_POR, required = false) String ordenarMovimentacoesPor,
            @RequestParam(name = "ordem",
                    defaultValue = ParametrosDeBusca.ORDEM, required = false) String ordem) {
        Response<MovimentacaoDTO> movimetacoesPorMesAno = movimentacaoService.buscarMovimentacaoPorPeriodo(
                mesInicio, mesFinal, anoInicio, anoFinal, numeroDaPagina, tamanhoDaPagina, ordenarMovimentacoesPor, ordem);
        return new ResponseEntity<>(movimetacoesPorMesAno, HttpStatus.OK);
    }

    @PostMapping("/movimentacao/{tipo}/{quantidade}/item/{itemId}")
    public ResponseEntity<MovimentacaoDTO> entrada(@PathVariable String tipo, @PathVariable Integer itemId, @PathVariable Integer quantidade) {
        MovimentacaoDTO movimentacao = movimentacaoService.movimentacao(
                tipo.equalsIgnoreCase("entrada") ? "entrada" : "saida",
                itemId,
                quantidade
        );
        return new ResponseEntity<>(movimentacao, HttpStatus.OK);
    }

    @PutMapping("/movimentacao/{id}/update")
    public ResponseEntity<MovimentacaoDTO> updateMovimentacao(@RequestBody MovimentacaoDTO movimentacao, @PathVariable Integer id) {
        MovimentacaoDTO updatedMovimentacao = movimentacaoService.updateMovimentacao(movimentacao, id);
        return new ResponseEntity<>(updatedMovimentacao, HttpStatus.OK);
    }

    @DeleteMapping("/movimentacao/{id}/delete")
    public ResponseEntity<MovimentacaoDTO> deletarMovimentacao(@PathVariable Integer id) {
        MovimentacaoDTO deletedMovimentacao = movimentacaoService.deletarMovimentacao(id);
        return new ResponseEntity<>(deletedMovimentacao, HttpStatus.OK);
    }
}