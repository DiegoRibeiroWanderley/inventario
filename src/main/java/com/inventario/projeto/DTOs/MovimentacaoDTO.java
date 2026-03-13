package com.inventario.projeto.DTOs;

import com.inventario.projeto.model.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoDTO {

    private Integer id;
    private ItemDTO itemDTO;
    private Integer quantidade;
    private String reponsavel;
}
