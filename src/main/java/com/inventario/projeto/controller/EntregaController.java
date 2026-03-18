package com.inventario.projeto.controller;

import com.inventario.projeto.model.enums.ParametrosDeBusca;
import com.inventario.projeto.payload.DTO.EntregaDTO;
import com.inventario.projeto.payload.Response;
import com.inventario.projeto.service.EntregaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
