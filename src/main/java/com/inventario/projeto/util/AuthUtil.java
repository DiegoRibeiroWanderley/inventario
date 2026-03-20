package com.inventario.projeto.util;

import com.inventario.projeto.exception.NotFoundException;
import com.inventario.projeto.model.Usuario;
import com.inventario.projeto.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final UsuarioRepository usuarioRepository;

    public Usuario getUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return usuarioRepository.findByNomeDoUsuario(authentication.getName())
                .orElseThrow(() -> new NotFoundException("Usuario", "Esse usuário não foi encontrado."));
    }
}
