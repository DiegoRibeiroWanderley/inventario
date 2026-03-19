package com.inventario.projeto.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventario.projeto.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails{

    private Integer id;
    private String nomeDoUsuario;
    private String email;

    @JsonIgnore
    private String senha;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(Usuario user){
        List<GrantedAuthority> grantedAuthorities = user.getFuncoes().stream()
                .map(role -> new SimpleGrantedAuthority(role.getFuncaoNome().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getUsuarioId(),
                user.getNomeDoUsuario(),
                user.getEmail(),
                user.getSenha(),
                grantedAuthorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return nomeDoUsuario;
    }
}
