package com.inventario.projeto.repositories;

import com.inventario.projeto.model.Funcao;
import com.inventario.projeto.model.enums.Funcoes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuncaoRepository extends JpaRepository<Funcao, Integer> {
    Optional<Funcao> findByFuncaoNome(Funcoes funcaoNome);
}
