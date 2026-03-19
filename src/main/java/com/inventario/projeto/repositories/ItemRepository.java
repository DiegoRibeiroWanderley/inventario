package com.inventario.projeto.repositories;

import com.inventario.projeto.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("select i from Item i where i.quantidadeEmEstoque < i.quantidadeMinima")
    Page<Item> findItemEmAlerta(Pageable paginacao);

    @Query("select sum(i.valorGanho) from Item i")
    Double valorTotal();
}
