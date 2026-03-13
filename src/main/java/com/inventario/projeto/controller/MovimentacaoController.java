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
