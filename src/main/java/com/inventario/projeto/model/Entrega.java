package com.inventario.projeto.model;

import com.inventario.projeto.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entregas")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer entregaId;

    @ManyToOne
    @JoinColumn(name = "movimentaco_id")
    private Movimentacao movimentacao;

    private LocalDate dataDespacho;

    private Double valorFrete;
    private Status status;
    private String transportadora;

    @PrePersist
    public void prePersist() {
        dataDespacho = LocalDate.now();
    }
}
