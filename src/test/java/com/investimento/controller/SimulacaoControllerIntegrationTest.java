package com.investimento.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investimento.dto.SimulacaoRequest;
import com.investimento.entity.Simulacao;
import com.investimento.repository.SimulacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class SimulacaoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SimulacaoRepository simulacaoRepository;

    private SimulacaoRequest simulacaoRequest;

    @BeforeEach
    void setUp() {
        // Limpar e preparar banco de testes
        simulacaoRepository.deleteAll();
        
        // Configurar request de teste
        simulacaoRequest = new SimulacaoRequest();
        simulacaoRequest.setClienteId(123L);
        simulacaoRequest.setValor(10000.00);
        simulacaoRequest.setPrazoMeses(12);
        simulacaoRequest.setTipoProduto("CDB");

        // Criar algumas simulações de teste
        Simulacao simulacao1 = new Simulacao(123L, "CDB Banco ABC", 5000.00, 5600.00, 12, "CDB");
        Simulacao simulacao2 = new Simulacao(456L, "Fundo XPTO", 10000.00, 11200.00, 12, "Fundo");
        Simulacao simulacao3 = new Simulacao(123L, "Tesouro Direto", 8000.00, 8480.00, 12, "Tesouro");
        
        simulacaoRepository.saveAll(List.of(simulacao1, simulacao2, simulacao3));
    }

    @Test
    void testSimularInvestimento_Integration() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/simular-investimento")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(simulacaoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.produtoValidado").exists())
                .andExpect(jsonPath("$.produtoValidado.nome").exists())
                .andExpect(jsonPath("$.resultadoSimulacao").exists())
                .andExpect(jsonPath("$.resultadoSimulacao.valorFinal").isNumber())
                .andExpect(jsonPath("$.dataSimulacao").exists());
    }

    @Test
    void testObterTodasSimulacoes_Integration() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/simulacoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].clienteId", containsInAnyOrder(123, 456, 123)))
                .andExpect(jsonPath("$[*].produto", everyItem(notNullValue())));
    }

    @Test
    void testObterSimulacoesPorCliente_Integration() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/simulacoes/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].clienteId", is(123)))
                .andExpect(jsonPath("$[1].clienteId", is(123)));
    }

    @Test
    void testObterSimulacoesPorCliente_ClienteInexistente() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/simulacoes/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testFluxoCompleto_SimularEDepoisListar() throws Exception {
        // Arrange - Nova simulação
        SimulacaoRequest novaSimulacao = new SimulacaoRequest();
        novaSimulacao.setClienteId(789L);
        novaSimulacao.setValor(15000.00);
        novaSimulacao.setPrazoMeses(24);
        novaSimulacao.setTipoProduto("Ações");

        // Act 1 - Fazer simulação
        mockMvc.perform(post("/api/simular-investimento")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novaSimulacao)))
                .andExpect(status().isOk());

        // Act 2 - Verificar se foi salva
        mockMvc.perform(get("/api/simulacoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4))); // 3 iniciais + 1 nova

        // Act 3 - Verificar simulações do cliente específico
        mockMvc.perform(get("/api/simulacoes/789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].clienteId", is(789)));
    }
}