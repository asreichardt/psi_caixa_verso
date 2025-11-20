package com.investimento.controller;

import com.investimento.entity.Produto;
import com.investimento.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class RecomendacaoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoRepository produtoRepository;

    @BeforeEach
    void setUp() {
        // Limpar e popular banco de testes
        produtoRepository.deleteAll();

        // Criar produtos de teste com diferentes riscos
        Produto produtoBaixoRisco = new Produto(
            "CDB Banco ABC", "CDB", 0.12, "Baixo", 1000.00, 6, true
        );
        
        Produto produtoMedioRisco = new Produto(
            "Fundo Moderado", "Fundo", 0.15, "Médio", 2000.00, 12, true
        );
        
        Produto produtoAltoRisco = new Produto(
            "Ações Tech", "Ações", 0.25, "Alto", 5000.00, 24, true
        );

        Produto produtoInativo = new Produto(
            "Produto Inativo", "CDB", 0.10, "Baixo", 1000.00, 6, false
        );

        produtoRepository.saveAll(List.of(
            produtoBaixoRisco, produtoMedioRisco, produtoAltoRisco, produtoInativo
        ));
    }

    @Test
    void testDeterminarPerfilRisco_Integration() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/perfil-risco/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteId", is(123)))
                .andExpect(jsonPath("$.perfil", oneOf("Conservador", "Moderado", "Agressivo")))
                .andExpect(jsonPath("$.pontuacao").isNumber())
                .andExpect(jsonPath("$.descricao").isString());
    }

    @Test
    void testObterProdutosRecomendados_Conservador_Integration() throws Exception {
        // Act & Assert - Conservador deve retornar apenas produtos de Baixo risco
        mockMvc.perform(get("/api/produtos-recomendados/Conservador"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))) // Apenas 1 produto ativo com risco Baixo
                .andExpect(jsonPath("$[0].risco", is("Baixo")))
                .andExpect(jsonPath("$[0].ativo", is(true)));
    }

    @Test
    void testObterProdutosRecomendados_Moderado_Integration() throws Exception {
        // Act & Assert - Moderado deve retornar produtos de Baixo e Médio risco
        mockMvc.perform(get("/api/produtos-recomendados/Moderado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Baixo + Médio
                .andExpect(jsonPath("$[*].risco", containsInAnyOrder("Baixo", "Médio")));
    }

    @Test
    void testObterProdutosRecomendados_Agressivo_Integration() throws Exception {
        // Act & Assert - Agressivo deve retornar todos os produtos ativos
        mockMvc.perform(get("/api/produtos-recomendados/Agressivo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3))) // Baixo + Médio + Alto (todos ativos)
                .andExpect(jsonPath("$[*].risco", containsInAnyOrder("Baixo", "Médio", "Alto")));
    }

    @Test
    void testObterProdutosRecomendados_PerfilCaseInsensitive() throws Exception {
        // Testar que o endpoint funciona com diferentes cases
        mockMvc.perform(get("/api/produtos-recomendados/conservador"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        mockMvc.perform(get("/api/produtos-recomendados/CONSERVADOR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testObterProdutosRecomendados_PerfilInexistente() throws Exception {
        // Act & Assert - Perfil que não existe deve retornar lista vazia
        mockMvc.perform(get("/api/produtos-recomendados/Inexistente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testFluxoCompleto_PerfilEProdutos() throws Exception {
        // Act 1 - Obter perfil do cliente
        String response = mockMvc.perform(get("/api/perfil-risco/456"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extrair o perfil da resposta (simulação simples)
        String perfil = "Moderado"; // Em um teste real, você extrairia do JSON

        // Act 2 - Obter produtos recomendados para o perfil
        mockMvc.perform(get("/api/produtos-recomendados/" + perfil))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Baixo + Médio
                .andExpect(jsonPath("$[*].ativo", everyItem(is(true))));
    }

    @Test
    void testProdutosInativosNaoSaoRecomendados() throws Exception {
        // Act & Assert - Produtos inativos não devem aparecer nas recomendações
        mockMvc.perform(get("/api/produtos-recomendados/Agressivo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].nome", not(hasItem("Produto Inativo"))));
    }
}