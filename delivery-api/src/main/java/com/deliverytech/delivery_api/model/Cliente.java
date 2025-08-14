package com.deliverytech.delivery_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity // Marca como entidade JPA.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // Facilita construção de objetos.
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Chave primária a
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique =true) // Email único.
    private String email;

    @Column(nullable = false)
    private String telefone;

    @Builder.Default
    private Boolean ativo = true;

}