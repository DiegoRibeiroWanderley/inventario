package com.inventario.projeto.security.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Username must not be blank")
    private String nomeDoUsuario;

    @NotBlank(message = "Password must not be blank")
    private String senha;
}
