package com.inventario.projeto.payload.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {

    private Integer categoriaId;

    @NotBlank(message = "A categoria deve possuir um nome")
    @Size(min = 3, max = 30, message = "O nome da categoria deve possuir entre 3 e 30 caracteres")
    private String nome;
}
