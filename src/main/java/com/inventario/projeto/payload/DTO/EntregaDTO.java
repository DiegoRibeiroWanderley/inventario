package com.inventario.projeto.payload.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntregaDTO {
    private Long entregaId;
    private Integer movimentacaoId;
    private Double valorFrete;
    private String status;
    private String transportadora;
}
