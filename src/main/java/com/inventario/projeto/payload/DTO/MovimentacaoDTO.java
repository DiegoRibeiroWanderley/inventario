package com.inventario.projeto.payload.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoDTO {

    private Integer id;
    private ItemDTO itemDTO;
    private String tipo;
    private Integer quantidade;
    private LocalDate dataMovimentacao;
    private String reponsavel;
}
