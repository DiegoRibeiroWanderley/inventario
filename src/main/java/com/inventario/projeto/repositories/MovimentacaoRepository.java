package com.inventario.projeto.repositories;

import com.inventario.projeto.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Integer> {
    List<Movimentacao> findByItemId(Integer itemId);

    List<Movimentacao> findMovimentacaoByDataMovimentacaoBetween(LocalDate inicio, LocalDate fim);
}