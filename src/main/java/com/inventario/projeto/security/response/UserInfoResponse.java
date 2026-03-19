package com.inventario.projeto.security.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {

    private Integer id;
    private String nomeDoUsuario;
    private List<String> funcoes;
}
