package com.inventario.projeto.repositories;

import com.inventario.projeto.model.Pedido;
import com.inventario.projeto.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    Pedido findPedidoByStatus(Status status);
}
