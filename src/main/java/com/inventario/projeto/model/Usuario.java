package com.inventario.projeto.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "nomeDoUsuario"),
                @UniqueConstraint(columnNames = "email")
        })
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer usuarioId;

    private String nomeDoUsuario;
    private String email;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role")
    private Set<Funcao> funcoes;

    private String senha;
}
