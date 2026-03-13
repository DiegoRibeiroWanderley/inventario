package com.inventario.projeto.repositories;

import com.inventario.projeto.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Integer> {
    List<Movimentacao> findByItemId(Integer itemId);
}
