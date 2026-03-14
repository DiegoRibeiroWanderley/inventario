package com.inventario.projeto.controller;

import com.inventario.projeto.DTOs.MovimentacaoDTO;
import com.inventario.projeto.service.MovimentacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    @GetMapping("/movimentacoes")
    public ResponseEntity<List<MovimentacaoDTO>> todasMovimentacoes() {
        List<MovimentacaoDTO> movimentacoes = movimentacaoService.todasMovimentacoes();
        return new ResponseEntity<>(movimentacoes, HttpStatus.OK);
    }

    @GetMapping("/movimentacoes/item/{id}")
    public ResponseEntity<List<MovimentacaoDTO>> buscarMovimentacaoPorItem(@PathVariable Integer id) {
        List<MovimentacaoDTO> movimentacoesDoItem = movimentacaoService.buscarMovimentacoesPorItem(id);
        return new ResponseEntity<>(movimentacoesDoItem, HttpStatus.OK);
    }

    @GetMapping("/movimentacoes/mês/{mes}/ano/{ano}")
    public ResponseEntity<List<MovimentacaoDTO>> buscarMovimentacaoPorMesAno(@PathVariable String mes, @PathVariable Integer ano){
        List<MovimentacaoDTO> movimetacoesPorMesAno = movimentacaoService.buscarMovimentacaoPorMesAno(mes, ano);
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
}
