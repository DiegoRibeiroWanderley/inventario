package com.inventario.projeto.repositories;

import com.inventario.projeto.model.Entrega;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Integer> {
    Page<Entrega> findEntregaByMovimentacao_Id(Integer movimentacaoId, Pageable paginacao);

    Page<Entrega> findEntregaByMovimentacao_Item_Id(Integer itemId, Pageable paginacao);

    @Query("select e from Entrega e where e.dataDespacho >= ?1 and e.dataDespacho <= ?2")
    Page<Entrega> findEntregaByPeriodo(LocalDate inicio, LocalDate fim, Pageable paginacao);
}
