package com.inventario.projeto.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponse {

    private List<ItemDTO> content;
    private Integer numeroDaPagina;
    private Integer tamanhoDaPagina;
    private Long totalDeElementos;
    private Integer totalDePaginas;
    private boolean ultimaPagina;
}
