package com.inventario.projeto.security.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank(message = "Nome do usuário não deve estar em branco")
    @Size(min = 3, max = 30, message = "Nome do usuário deve ter entre 3 e 30 caracteres")
    private String nomeDoUsuario;

    @NotBlank(message = "Email não pode estar em branco")
    @Size(max = 50, message = "Email deve ter até 50 caracteres")
    @Email
    private String email;

    private Set<String> funcao;

    @NotBlank(message = "Senha não pode estar em branco")
    @Size(min = 8, max = 20, message = "Senha deve ter entre 8 e 20 caracteres")
    private String senha;
}
