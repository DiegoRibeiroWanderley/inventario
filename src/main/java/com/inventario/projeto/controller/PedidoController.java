package com.inventario.projeto.controller;

import com.inventario.projeto.payload.DTO.PedidoDTO;
import com.inventario.projeto.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping("/pedido/item/{itemId}/qtde/{quantidade}")
    public ResponseEntity<PedidoDTO> criarPedido(@PathVariable Integer itemId, @PathVariable Integer quantidade) {
        PedidoDTO pedido = pedidoService.adicionarItemAoPedido(itemId, quantidade);
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @PostMapping("/pedido/lancar")
    public ResponseEntity<PedidoDTO> lancarPedido() {
        PedidoDTO pedido = pedidoService.lancarPedido();
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @DeleteMapping("/pedido/deletar/{pedidoId}")
    public ResponseEntity<PedidoDTO> deletarPedido(@PathVariable Integer pedidoId) {
        PedidoDTO pedido = pedidoService.deletarPedido(pedidoId);
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }
}
