package com.inventario.projeto.repositories;

import com.inventario.projeto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNomeDoUsuario(String username);

    Boolean existsByNomeDoUsuario(String username);

    boolean existsByEmail(String email);
}
