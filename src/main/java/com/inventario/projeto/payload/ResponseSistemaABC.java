package com.inventario.projeto.payload;

import com.inventario.projeto.payload.DTO.ItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseSistemaABC {

    private List<ItemDTO> itemsA;
    private List<ItemDTO> itemsB;
    private List<ItemDTO> itemsC;
    private Integer numeroDaPagina;
    private Integer tamanhoDaPagina;
    private Long totalDeElementos;
    private Integer totalDePaginas;
    private boolean ultimaPagina;
}
