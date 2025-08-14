package com.deliverytech.delivery_api.config;

import com.deliverytech.delivery_api.model.Cliente;
import com.deliverytech.delivery_api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype. Component;
import java.util.List;

/**
* Seeds initial Cliente data for local runs and tests.
* Idempotent: checks email uniqueness before inserting.
*/

@Component // Torna a classe um bean gerenciado pelo Spring.
@RequiredArgsConstructor // Gera construtor com os campos finais (injeção d
public class DataLoader implements CommandLineRunner {
    private final ClienteRepository clienteRepository;
    @Override
        public void run(String... args) {
        // Cria clientes de exemplo para testes.
        List<Cliente> seeds =List.of(
            Cliente.builder()
            .nome("João Silva")
            .email("joao.silva@teste.com")
            .telefone ("11988880001")
            .ativo(true)
            .build(),
            Cliente.builder()
            .nome("Ana Souza")
            .email("ana.souza@teste.com")
            .telefone ("21988880002")
            .ativo(true)
            .build(),
            Cliente.builder()
            .nome("Pedro Santos")
            .email("pedro.santos@teste.com")
            .telefone ("31988880003")
            .ativo(false)
            .build());
            // Insere apenas se o email não existir, evitando duplicidade.
            seeds.forEach(c -> {
                if (!clienteRepository.existsByEmail(c.getEmail())) {
                    clienteRepository.save(c);}
            });
        }
}




