package com.investimento.controller;

import com.investimento.entity.Telemetria;
import com.investimento.repository.TelemetriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class TelemetriaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TelemetriaRepository telemetriaRepository;

    @BeforeEach
    void setUp() {
        // Limpar dados de teste
        telemetriaRepository.deleteAll();
    }

    @Test
    void testObterTelemetria_ComDadosNoPeriodo() throws Exception {
        // Arrange - Criar dados de telemetria no período
        LocalDateTime dataNoPeriodo = LocalDateTime.of(2025, 10, 15, 10, 30);
        
        Telemetria telemetria1 = new Telemetria("simular-investimento", 250L, true);
        telemetria1.setDataChamada(dataNoPeriodo);
        
        Telemetria telemetria2 = new Telemetria("perfil-risco", 180L, true);
        telemetria2.setDataChamada(dataNoPeriodo);
        
        Telemetria telemetria3 = new Telemetria("simular-investimento", 300L, false);
        telemetria3.setDataChamada(dataNoPeriodo);
        
        telemetriaRepository.saveAll(List.of(telemetria1, telemetria2, telemetria3));

        // Act & Assert
        mockMvc.perform(get("/api/telemetria")
                .param("inicio", "2025-10-01")
                .param("fim", "2025-10-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.servicos", hasSize(2))) // 2 serviços distintos
                .andExpect(jsonPath("$.periodo.inicio", is("2025-10-01")))
                .andExpect(jsonPath("$.periodo.fim", is("2025-10-31")))
                .andExpect(jsonPath("$.servicos[*].nomeServico", containsInAnyOrder("simular-investimento", "perfil-risco")))
                .andExpect(jsonPath("$.servicos[?(@.nomeServico == 'simular-investimento')].quantidadeChamadas", hasItem(2)))
                .andExpect(jsonPath("$.servicos[?(@.nomeServico == 'perfil-risco')].quantidadeChamadas", hasItem(1)));
    }

    @Test
    void testObterTelemetria_SemDadosNoPeriodo() throws Exception {
        // Arrange - Criar dados fora do período
        LocalDateTime dataForaPeriodo = LocalDateTime.of(2025, 9, 15, 10, 30);
        
        Telemetria telemetria = new Telemetria("simular-investimento", 250L, true);
        telemetria.setDataChamada(dataForaPeriodo);
        telemetriaRepository.save(telemetria);

        // Act & Assert - Período diferente deve retornar lista vazia
        mockMvc.perform(get("/api/telemetria")
                .param("inicio", "2025-10-01")
                .param("fim", "2025-10-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.servicos", hasSize(0))) // Nenhum dado no período
                .andExpect(jsonPath("$.periodo.inicio", is("2025-10-01")))
                .andExpect(jsonPath("$.periodo.fim", is("2025-10-31")));
    }

    @Test
    void testObterTelemetria_ParametrosPadrao() throws Exception {
        // Arrange - Criar alguns dados
        Telemetria telemetria1 = new Telemetria("simular-investimento", 250L, true);
        Telemetria telemetria2 = new Telemetria("perfil-risco", 180L, true);
        telemetriaRepository.saveAll(List.of(telemetria1, telemetria2));

        // Act & Assert - Usar parâmetros padrão (2025-10-01 a 2025-10-31)
        mockMvc.perform(get("/api/telemetria")) // Sem parâmetros
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.servicos").exists())
                .andExpect(jsonPath("$.periodo.inicio", is("2025-10-01")))
                .andExpect(jsonPath("$.periodo.fim", is("2025-10-31")));
    }

    @Test
    void testObterTelemetria_PeriodoEspecifico() throws Exception {
        // Arrange - Criar dados em períodos específicos
        LocalDateTime dataOutubro = LocalDateTime.of(2025, 10, 15, 10, 30);
        LocalDateTime dataNovembro = LocalDateTime.of(2025, 11, 15, 10, 30);
        
        Telemetria telemetriaOutubro = new Telemetria("servico-outubro", 200L, true);
        telemetriaOutubro.setDataChamada(dataOutubro);
        
        Telemetria telemetriaNovembro = new Telemetria("servico-novembro", 300L, true);
        telemetriaNovembro.setDataChamada(dataNovembro);
        
        telemetriaRepository.saveAll(List.of(telemetriaOutubro, telemetriaNovembro));

        // Act & Assert 1 - Buscar apenas outubro
        mockMvc.perform(get("/api/telemetria")
                .param("inicio", "2025-10-01")
                .param("fim", "2025-10-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.servicos", hasSize(1)))
                .andExpect(jsonPath("$.servicos[0].nomeServico", is("servico-outubro")));

        // Act & Assert 2 - Buscar apenas novembro
        mockMvc.perform(get("/api/telemetria")
                .param("inicio", "2025-11-01")
                .param("fim", "2025-11-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.servicos", hasSize(1)))
                .andExpect(jsonPath("$.servicos[0].nomeServico", is("servico-novembro")));
    }

    @Test
    void testObterTelemetria_AgrupamentoPorServico() throws Exception {
        // Arrange - Múltiplas chamadas para o mesmo serviço
        LocalDateTime data1 = LocalDateTime.of(2025, 10, 10, 9, 0);
        LocalDateTime data2 = LocalDateTime.of(2025, 10, 10, 14, 0);
        LocalDateTime data3 = LocalDateTime.of(2025, 10, 11, 11, 0);
        
        Telemetria t1 = new Telemetria("simular-investimento", 200L, true);
        t1.setDataChamada(data1);
        
        Telemetria t2 = new Telemetria("simular-investimento", 300L, true);
        t2.setDataChamada(data2);
        
        Telemetria t3 = new Telemetria("simular-investimento", 250L, false);
        t3.setDataChamada(data3);
        
        Telemetria t4 = new Telemetria("perfil-risco", 150L, true);
        t4.setDataChamada(data1);
        
        telemetriaRepository.saveAll(List.of(t1, t2, t3, t4));

        // Act & Assert - Verificar agrupamento e cálculos
        mockMvc.perform(get("/api/telemetria")
                .param("inicio", "2025-10-01")
                .param("fim", "2025-10-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.servicos", hasSize(2)))
                .andExpect(jsonPath("$.servicos[?(@.nomeServico == 'simular-investimento')].quantidadeChamadas", hasItem(3)))
                .andExpect(jsonPath("$.servicos[?(@.nomeServico == 'perfil-risco')].quantidadeChamadas", hasItem(1)));
    }

    @Test
    void testObterTelemetria_CalculoMediaTempoResposta() throws Exception {
        // Arrange - Dados para testar cálculo de média
        LocalDateTime data = LocalDateTime.of(2025, 10, 15, 10, 0);
        
        Telemetria t1 = new Telemetria("servico-teste", 100L, true);
        t1.setDataChamada(data);
        
        Telemetria t2 = new Telemetria("servico-teste", 200L, true);
        t2.setDataChamada(data);
        
        Telemetria t3 = new Telemetria("servico-teste", 300L, true);
        t3.setDataChamada(data);
        
        telemetriaRepository.saveAll(List.of(t1, t2, t3));

        // Act & Assert - Média deve ser (100+200+300)/3 = 200
        mockMvc.perform(get("/api/telemetria")
                .param("inicio", "2025-10-01")
                .param("fim", "2025-10-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.servicos", hasSize(1)))
                .andExpect(jsonPath("$.servicos[0].nomeServico", is("servico-teste")))
                .andExpect(jsonPath("$.servicos[0].quantidadeChamadas", is(3)))
                .andExpect(jsonPath("$.servicos[0].mediaTempoRespostaMs", is(200L))); // (100+200+300)/3
    }

    @Test
    void testObterTelemetria_ComSucessoEFalha() throws Exception {
        // Arrange - Chamadas com sucesso e falha
        LocalDateTime data = LocalDateTime.of(2025, 10, 15, 10, 0);
        
        Telemetria sucesso1 = new Telemetria("servico-teste", 100L, true);
        sucesso1.setDataChamada(data);
        
        Telemetria sucesso2 = new Telemetria("servico-teste", 150L, true);
        sucesso2.setDataChamada(data);
        
        Telemetria falha = new Telemetria("servico-teste", 50L, false);
        falha.setDataChamada(data);
        
        telemetriaRepository.saveAll(List.of(sucesso1, sucesso2, falha));

        // Act & Assert - Todas as chamadas devem ser contabilizadas, independente do sucesso
        mockMvc.perform(get("/api/telemetria")
                .param("inicio", "2025-10-01")
                .param("fim", "2025-10-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.servicos[0].quantidadeChamadas", is(3))) // 3 chamadas no total
                .andExpect(jsonPath("$.servicos[0].mediaTempoRespostaMs", is(100L))); // (100+150+50)/3
    }

    @Test
    void testObterTelemetria_PeriodoUmDia() throws Exception {
        // Arrange - Dados em um único dia
        LocalDateTime data = LocalDateTime.of(2025, 10, 15, 10, 0);
        
        Telemetria t1 = new Telemetria("servico-dia", 100L, true);
        t1.setDataChamada(data);
        
        Telemetria t2 = new Telemetria("servico-dia", 200L, true);
        t2.setDataChamada(data.plusHours(1));
        
        telemetriaRepository.saveAll(List.of(t1, t2));

        // Act & Assert - Buscar apenas um dia específico
        mockMvc.perform(get("/api/telemetria")
                .param("inicio", "2025-10-15")
                .param("fim", "2025-10-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.servicos", hasSize(1)))
                .andExpect(jsonPath("$.servicos[0].quantidadeChamadas", is(2)))
                .andExpect(jsonPath("$.periodo.inicio", is("2025-10-15")))
                .andExpect(jsonPath("$.periodo.fim", is("2025-10-15")));
    }

    @Test
    void testObterTelemetria_DatasInvalidas() throws Exception {
        // Act & Assert - Datas em formato inválido
        mockMvc.perform(get("/api/telemetria")
                .param("inicio", "data-invalida")
                .param("fim", "2025-10-31"))
                .andExpect(status().isBadRequest()); // Deve retornar erro 400
    }

    @Test
    void testObterTelemetria_PeriodoInvertido() throws Exception {
        // Arrange - Alguns dados
        Telemetria telemetria = new Telemetria("servico-teste", 100L, true);
        telemetriaRepository.save(telemetria);

        // Act & Assert - Data início depois da data fim
        mockMvc.perform(get("/api/telemetria")
                .param("inicio", "2025-10-31")
                .param("fim", "2025-10-01"))
                .andExpect(status().isOk()) // A API aceita, mas retorna lista vazia
                .andExpect(jsonPath("$.servicos", hasSize(0)));
    }
}