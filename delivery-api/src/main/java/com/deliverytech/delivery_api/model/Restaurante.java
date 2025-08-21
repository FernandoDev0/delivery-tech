package com.deliverytech.delivery_api.model;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String cnpj;

    private String endereco;
    private String telefone;
    private String especialidade;

    @Column(length = 1000)
    private String descricao;

    @Column(nullable = false)
    private String horarioFuncionamento;

    @Builder.Default
    private Boolean ativo = true;

    @Builder.Default
    private LocalDateTime dataCadastro = LocalDateTime.now();

    // Relacionamento com Produtos
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
    private List<Produto> produtos = new ArrayList<>();

    // Relacionamento com Pedidos
    @OneToMany(mappedBy = "restaurante")
    @JsonIgnore
    private List<Pedido> pedidos = new ArrayList<>();

    // Método auxiliar para inativar restaurante
    @JsonIgnore
    public void inativar() {
        this.ativo = false;
    }
    
    // Método auxiliar para adicionar produto
    @JsonIgnore
    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
        produto.setRestaurante(this);
    }
}
