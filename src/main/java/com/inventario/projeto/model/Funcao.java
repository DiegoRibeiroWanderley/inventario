package com.inventario.projeto.model;

import com.inventario.projeto.model.enums.Funcoes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Funcao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer funcaoId;

    @Enumerated(EnumType.STRING)
    private Funcoes funcaoNome;
}
