package com.inventario.projeto.controller;

import com.inventario.projeto.model.enums.ParametrosDeBusca;
import com.inventario.projeto.payload.DTO.EntregaDTO;
import com.inventario.projeto.payload.Response;
import com.inventario.projeto.service.EntregaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EntregaController {

    private final EntregaService entregaService;

    @GetMapping("/entregas")
    public ResponseEntity<Response<EntregaDTO>> findAll(
            @RequestParam(name = "numeroDaPagina",
                    defaultValue = ParametrosDeBusca.NUMERO_DA_PAGINA, required = false) Integer numeroDaPagina,
            @RequestParam(name = "tamanhoDaPagina",
                    defaultValue = ParametrosDeBusca.TAMANHO_DA_PAGINA, required = false) Integer tamanhoDaPagina,
            @RequestParam(name = "ordenarItemsPor",
                    defaultValue = ParametrosDeBusca.ORDENAR_ENTREGAS_POR, required = false) String ordenarEntregasPor,
            @RequestParam(name = "ordem",
                    defaultValue = ParametrosDeBusca.ORDEM, required = false) String ordem) {
        Response<EntregaDTO> response = entregaService.findAll(numeroDaPagina, tamanhoDaPagina, ordenarEntregasPor, ordem);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/entregas/movimentacao/{movimentacaoId}")
    public ResponseEntity<Response<EntregaDTO>> findByMovimentacao(
            @PathVariable Integer movimentacaoId,
            @RequestParam(name = "numeroDaPagina",
                    defaultValue = ParametrosDeBusca.NUMERO_DA_PAGINA, required = false) Integer numeroDaPagina,
            @RequestParam(name = "tamanhoDaPagina",
                    defaultValue = ParametrosDeBusca.TAMANHO_DA_PAGINA, required = false) Integer tamanhoDaPagina,
            @RequestParam(name = "ordenarItemsPor",
                    defaultValue = ParametrosDeBusca.ORDENAR_ENTREGAS_POR, required = false) String ordenarEntregasPor,
            @RequestParam(name = "ordem",
                    defaultValue = ParametrosDeBusca.ORDEM, required = false) String ordem) {
        Response<EntregaDTO> response = entregaService.findByMovimentacao(numeroDaPagina, tamanhoDaPagina, ordenarEntregasPor, ordem, movimentacaoId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/entregas/item/{itemId}")
    public ResponseEntity<Response<EntregaDTO>> findByItem(
            @PathVariable Integer itemId,
            @RequestParam(name = "numeroDaPagina",
                    defaultValue = ParametrosDeBusca.NUMERO_DA_PAGINA, required = false) Integer numeroDaPagina,
            @RequestParam(name = "tamanhoDaPagina",
                    defaultValue = ParametrosDeBusca.TAMANHO_DA_PAGINA, required = false) Integer tamanhoDaPagina,
            @RequestParam(name = "ordenarItemsPor",
                    defaultValue = ParametrosDeBusca.ORDENAR_ENTREGAS_POR, required = false) String ordenarEntregasPor,
            @RequestParam(name = "ordem",
                    defaultValue = ParametrosDeBusca.ORDEM, required = false) String ordem) {
        Response<EntregaDTO> response = entregaService.findByItem(numeroDaPagina, tamanhoDaPagina, ordenarEntregasPor, ordem, itemId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/entregas/{mesInicio}/{anoInicio}/{mesFinal}/{anoFinal}")
    public ResponseEntity<Response<EntregaDTO>> findByItem(
            @PathVariable String mesInicio, @PathVariable Integer anoInicio, @PathVariable String mesFinal, @PathVariable Integer anoFinal,
            @RequestParam(name = "numeroDaPagina",
                    defaultValue = ParametrosDeBusca.NUMERO_DA_PAGINA, required = false) Integer numeroDaPagina,
            @RequestParam(name = "tamanhoDaPagina",
                    defaultValue = ParametrosDeBusca.TAMANHO_DA_PAGINA, required = false) Integer tamanhoDaPagina,
            @RequestParam(name = "ordenarItemsPor",
                    defaultValue = ParametrosDeBusca.ORDENAR_ENTREGAS_POR, required = false) String ordenarEntregasPor,
            @RequestParam(name = "ordem",
                    defaultValue = ParametrosDeBusca.ORDEM, required = false) String ordem) {
        Response<EntregaDTO> response = entregaService.findByPeriodo(numeroDaPagina, tamanhoDaPagina, ordenarEntregasPor, ordem, mesInicio, anoInicio, mesFinal, anoFinal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/entrega/add/item/{itemId}/quantidade/{quantidade}")
    public ResponseEntity<EntregaDTO> createEntrega(@RequestBody EntregaDTO entregaDTO, @PathVariable Integer itemId, @PathVariable Integer quantidade) {
        EntregaDTO entrega = entregaService.createEntrega(entregaDTO, itemId, quantidade);
        return new ResponseEntity<>(entrega, HttpStatus.CREATED);
    }

    @PutMapping("/entrega/update/{entregaId}")
    public ResponseEntity<EntregaDTO> updateEntrega(@RequestBody EntregaDTO entregaDTO, @PathVariable Integer entregaId) {
        EntregaDTO updatedEntrega = entregaService.updateEntrega(entregaDTO, entregaId);
        return new ResponseEntity<>(updatedEntrega, HttpStatus.OK);
    }

    @DeleteMapping("/entrega/delete/{entregaId}")
    public ResponseEntity<EntregaDTO> deleteEntrega(@PathVariable Integer entregaId) {
        EntregaDTO deletedEntrega = entregaService.deleteEntrega(entregaId);
        return new ResponseEntity<>(deletedEntrega, HttpStatus.OK);
    }
}
