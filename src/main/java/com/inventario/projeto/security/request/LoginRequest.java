package com.inventario.projeto.security.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Nome do usuário não deve estar em branco")
    private String nomeDoUsuario;

    @NotBlank(message = "Senha não deve estar em braco")
    private String senha;
}
