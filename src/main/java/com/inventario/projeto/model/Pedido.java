package com.inventario.projeto.model;

import com.inventario.projeto.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "pedido", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<ItemPedido> itens;

    private Double precoItems;
    private Double precoTaxas;
    private Double precoTotal;
    private LocalDate dataPedido;
    private Status status;
}
