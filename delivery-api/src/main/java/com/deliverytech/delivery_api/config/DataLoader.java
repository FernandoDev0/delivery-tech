package com.deliverytech.delivery_api.config;

import com.deliverytech.delivery_api.model.*;
import com.deliverytech.delivery_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== INICIANDO CARGA DE DADOS DE TESTE ===");
        
        
        // Inserir dados de teste (sem limpar dados existentes)
        inserirClientes();
        inserirRestaurantes();
        
        

        System.out.println("=== CARGA DE DADOS CONCLUÍDA ===");
        
        // ✅ ADICIONAR: Spring Boot iniciada com sucesso + Bender
        System.out.println("\n✅ Spring Boot Application iniciada com sucesso!");
        
        
        // ✅ INFORMAR sobre captura automática
        System.out.println("\n🎯 SISTEMA DE CAPTURA AUTOMÁTICA ATIVO!");
        System.out.println("📁 Respostas serão salvas em: ./entregaveis/");
        System.out.println("🔄 Faça requisições para /api/* e veja os arquivos sendo gerados!\n");
    }


private void inserirClientes() {
    System.out.println("--- Inserindo clientes ---");

    Cliente cliente1 = new Cliente(null, "João Silva", "joao@email.com",
            "11987654321", "Rua das Flores, 123 - Vila Madalena, São Paulo - SP",
            null, true, null);

    Cliente cliente2 = new Cliente(null, "Maria Santos", "maria@email.com",
            "11876543210", "Av. Paulista, 456 - Bela Vista, São Paulo - SP",
            null, true, null);

    Cliente cliente3 = new Cliente(null, "Pedro Oliveira", "pedro@email.com",
            "11765432109", "Rua Augusta, 789 - Consolação, São Paulo - SP",
            null, false, null);

    Cliente cliente4 = new Cliente(null, "Ana Costa", "ana@email.com",
            "11654321098", "Rua Oscar Freire, 321 - Jardins, São Paulo - SP",
            null, true, null);

    Cliente cliente5 = new Cliente(null, "Carlos Ferreira", "carlos@email.com",
            "11543210987", "Rua 25 de Março, 654 - Centro, São Paulo - SP",
            null, true, null);

    List<Cliente> clientes = Arrays.asList(cliente1, cliente2, cliente3, cliente4, cliente5);

    for (Cliente cliente : clientes) {
        if (clienteRepository.findByEmail(cliente.getEmail()).isEmpty()) {
            clienteRepository.save(cliente);
            System.out.println("✓ Cliente " + cliente.getNome() + " inserido");
        } else {
            System.out.println("⚠ Cliente " + cliente.getNome() + " já existe, ignorando...");
        }
    }
}

private void inserirRestaurantes() {
    System.out.println("--- Inserindo Restaurantes ---");

    Restaurante restaurante1 = Restaurante.builder()
            .nome("Pizza Express")
            .categoria("Italiana")
            .telefone("1133333333")
            .TaxaEntrega(new BigDecimal("3.50"))
            .ativo(true)
            .build();

    Restaurante restaurante2 = Restaurante.builder()
            .nome("Burger King")
            .categoria("Fast Food")
            .telefone("1144444444")
            .TaxaEntrega(new BigDecimal("5.00"))
            .ativo(true)
            .build();

    Restaurante restaurante3 = Restaurante.builder()
            .nome("Sushi House")
            .categoria("Japonesa")
            .telefone("1155555555")
            .TaxaEntrega(new BigDecimal("4.00"))
            .ativo(true)
            .build();

    Restaurante restaurante4 = Restaurante.builder()
            .nome("Gyros Athenas")
            .categoria("Grega")
            .telefone("1166666666")
            .TaxaEntrega(new BigDecimal("3.80"))
            .ativo(true)
            .build();

    Restaurante restaurante5 = Restaurante.builder()
            .nome("Chiparia do Porto")
            .categoria("Portuguesa")
            .telefone("1177777777")
            .TaxaEntrega(new BigDecimal("4.50"))
            .ativo(true)
            .build();

    List<Restaurante> restaurantes = Arrays.asList(
            restaurante1, restaurante2, restaurante3, restaurante4, restaurante5
    );

    // Salvar restaurantes se não existirem
    for (Restaurante restaurante : restaurantes) {
        if (restauranteRepository.findByNome(restaurante.getNome()).isEmpty()) {
            restauranteRepository.save(restaurante);
            System.out.println("✓ Restaurante " + restaurante.getNome() + " inserido");
        } else {
            System.out.println("⚠ Restaurante " + restaurante.getNome() + " já existe, ignorando...");
        }
    }

    inserirProdutos(); // Chama produtos após salvar restaurantes
}



private void inserirProdutos() {
    System.out.println("--- Inserindo Produtos ---");

    // Buscar restaurantes para associar aos produtos
    var restaurantes = restauranteRepository.findAll();
    var pizzaExpress = restaurantes.stream().filter(r -> r.getNome().equals("Pizza Express")).findFirst().orElse(null);
    var burgerKing = restaurantes.stream().filter(r -> r.getNome().equals("Burger King")).findFirst().orElse(null);
    var sushiHouse = restaurantes.stream().filter(r -> r.getNome().equals("Sushi House")).findFirst().orElse(null);
    var gyrosAthenas = restaurantes.stream().filter(r -> r.getNome().equals("Gyros Athenas")).findFirst().orElse(null);
    var chipariaPorto = restaurantes.stream().filter(r -> r.getNome().equals("Chiparia do Porto")).findFirst().orElse(null);

    // Criar produtos com verificação de restaurante
    List<Produto> produtos = new ArrayList<>();

    if (pizzaExpress != null) {
        Produto produto1 = new Produto();
        produto1.setNome("Pizza Margherita");
        produto1.setCategoria("Pizza");
        produto1.setDescricao("Pizza clássica com molho de tomate, mussarela e manjericão");
        produto1.setPreco(new BigDecimal("25.90"));
        produto1.setRestaurante(pizzaExpress);
        produto1.setAtivo(true);
        produtos.add(produto1);

        Produto produto2 = new Produto();
        produto2.setNome("Pizza Pepperoni");
        produto2.setCategoria("Pizza");
        produto2.setDescricao("Pizza com molho de tomate, mussarela e pepperoni");
        produto2.setPreco(new BigDecimal("29.90"));
        produto2.setRestaurante(pizzaExpress);
        produto2.setAtivo(true);
        produtos.add(produto2);
    }

    if (burgerKing != null) {
        Produto produto3 = new Produto();
        produto3.setNome("Big Burger");
        produto3.setCategoria("Hambúrguer");
        produto3.setDescricao("Hambúrguer duplo com queijo, alface, tomate e molho especial");
        produto3.setPreco(new BigDecimal("18.50"));
        produto3.setRestaurante(burgerKing);
        produto3.setAtivo(true);
        produtos.add(produto3);

        Produto produto4 = new Produto();
        produto4.setNome("Batata Frita Grande");
        produto4.setCategoria("Acompanhamento");
        produto4.setDescricao("Porção grande de batatas fritas crocantes");
        produto4.setPreco(new BigDecimal("8.90"));
        produto4.setRestaurante(burgerKing);
        produto4.setAtivo(true);
        produtos.add(produto4);
    }

    if (sushiHouse != null) {
        Produto produto5 = new Produto();
        produto5.setNome("Sushi Salmão");
        produto5.setCategoria("Sushi");
        produto5.setDescricao("8 peças de sushi de salmão fresco");
        produto5.setPreco(new BigDecimal("32.00"));
        produto5.setRestaurante(sushiHouse);
        produto5.setAtivo(true);
        produtos.add(produto5);

        Produto produto6 = new Produto();
        produto6.setNome("Hot Roll");
        produto6.setCategoria("Sushi");
        produto6.setDescricao("8 peças de hot roll empanado com salmão");
        produto6.setPreco(new BigDecimal("28.50"));
        produto6.setRestaurante(sushiHouse);
        produto6.setAtivo(true);
        produtos.add(produto6);
    }

    if (gyrosAthenas != null) {
        Produto produto7 = new Produto();
        produto7.setNome("Gyros de Cordeiro");
        produto7.setCategoria("Espeto");
        produto7.setDescricao("Espeto de cordeiro grelhado com molho tzatziki, tomate e cebola roxa");
        produto7.setPreco(new BigDecimal("35.90"));
        produto7.setRestaurante(gyrosAthenas);
        produto7.setAtivo(true);
        produtos.add(produto7);

        Produto produto8 = new Produto();
        produto8.setNome("Souvlaki de Frango");
        produto8.setCategoria("Espeto");
        produto8.setDescricao("Espetinho de frango marinado com ervas gregas e batata frita");
        produto8.setPreco(new BigDecimal("28.50"));
        produto8.setRestaurante(gyrosAthenas);
        produto8.setAtivo(true);
        produtos.add(produto8);
    }

    if (chipariaPorto != null) {
        Produto produto9 = new Produto();
        produto9.setNome("Fish & Chips Tradicional");
        produto9.setCategoria("Peixe");
        produto9.setDescricao("Filé de bacalhau empanado com batatas fritas e molho tártaro");
        produto9.setPreco(new BigDecimal("42.90"));
        produto9.setRestaurante(chipariaPorto);
        produto9.setAtivo(true);
        produtos.add(produto9);

        Produto produto10 = new Produto();
        produto10.setNome("Porção de Camarão Empanado");
        produto10.setCategoria("Frutos do Mar");
        produto10.setDescricao("500g de camarão empanado com molho agridoce");
        produto10.setPreco(new BigDecimal("52.00"));
        produto10.setRestaurante(chipariaPorto);
        produto10.setAtivo(true);
        produtos.add(produto10);
    }

    // Salvar produtos no banco
    for (Produto produto : produtos) {
        if (produtoRepository.findByNome(produto.getNome()).isEmpty()) {
            produtoRepository.save(produto);
            System.out.println("✓ Produto " + produto.getNome() + " inserido");
        } else {
            System.out.println("⚠ Produto " + produto.getNome() + " já existe, ignorando...");
        }
    }
}



}




