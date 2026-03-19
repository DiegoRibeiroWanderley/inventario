package com.inventario.projeto.security.service;

import com.inventario.projeto.model.Usuario;
import com.inventario.projeto.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = userRepository.findByNomeDoUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));

        return UserDetailsImpl.build(user);
    }
}
