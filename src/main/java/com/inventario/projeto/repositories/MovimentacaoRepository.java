package com.inventario.projeto.repositories;

import com.inventario.projeto.model.Movimentacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Integer> {

    Page<Movimentacao> findByItemId(Integer itemId, Pageable pageable);

    Page<Movimentacao> findMovimentacaoByDataMovimentacaoBetween(LocalDate inicio, LocalDate fim, Pageable pageable);
}