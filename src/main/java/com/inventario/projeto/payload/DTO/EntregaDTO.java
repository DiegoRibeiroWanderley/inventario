package com.inventario.projeto.payload.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntregaDTO {
    private Integer entregaId;
    private Integer movimentacaoId;
    private LocalDateTime dataDespacho;
    private Double valorFrete;
    private String status;
    private String transportadora;
}
