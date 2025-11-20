package com.investimento.controller;

import com.investimento.service.TelemetriaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TelemetriaControllerTest {

    @Mock
    private TelemetriaService telemetriaService;

    @InjectMocks
    private TelemetriaController telemetriaController;

    @Test
    void testObterTelemetria_ComParametrosPadrao() {
        // Arrange
        TelemetriaService.MetricaServico metrica1 = 
            new TelemetriaService.MetricaServico("simular-investimento", 120, 250L);
        TelemetriaService.MetricaServico metrica2 = 
            new TelemetriaService.MetricaServico("perfil-risco", 80, 180L);
        
        List<TelemetriaService.MetricaServico> metricas = Arrays.asList(metrica1, metrica2);
        
        when(telemetriaService.obterMetricasPorPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(metricas);

        // Act
        ResponseEntity<Map<String, Object>> response = telemetriaController.obterTelemetria("2025-10-01", "2025-10-31");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        
        // Verificar serviços
        List<TelemetriaService.MetricaServico> servicosResponse = 
            (List<TelemetriaService.MetricaServico>) body.get("servicos");
        assertEquals(2, servicosResponse.size());
        assertEquals("simular-investimento", servicosResponse.get(0).getNomeServico());
        assertEquals(120, servicosResponse.get(0).getQuantidadeChamadas());
        assertEquals(250L, servicosResponse.get(0).getMediaTempoRespostaMs());
        
        // Verificar período
        Map<String, String> periodo = (Map<String, String>) body.get("periodo");
        assertEquals("2025-10-01", periodo.get("inicio"));
        assertEquals("2025-10-31", periodo.get("fim"));
        
        // Verificar que o serviço foi chamado com as datas corretas
        LocalDateTime dataInicioEsperada = LocalDate.parse("2025-10-01").atStartOfDay();
        LocalDateTime dataFimEsperada = LocalDate.parse("2025-10-31").atTime(LocalTime.MAX);
        verify(telemetriaService).obterMetricasPorPeriodo(dataInicioEsperada, dataFimEsperada);
    }

    @Test
    void testObterTelemetria_ComParametrosCustomizados() {
        // Arrange
        TelemetriaService.MetricaServico metrica = 
            new TelemetriaService.MetricaServico("simulacoes", 45, 150L);
        List<TelemetriaService.MetricaServico> metricas = Arrays.asList(metrica);
        
        when(telemetriaService.obterMetricasPorPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(metricas);

        // Act
        ResponseEntity<Map<String, Object>> response = telemetriaController.obterTelemetria("2025-09-01", "2025-09-30");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        
        // Verificar período customizado
        Map<String, String> periodo = (Map<String, String>) body.get("periodo");
        assertEquals("2025-09-01", periodo.get("inicio"));
        assertEquals("2025-09-30", periodo.get("fim"));
        
        // Verificar datas
        LocalDateTime dataInicioEsperada = LocalDate.parse("2025-09-01").atStartOfDay();
        LocalDateTime dataFimEsperada = LocalDate.parse("2025-09-30").atTime(LocalTime.MAX);
        verify(telemetriaService).obterMetricasPorPeriodo(dataInicioEsperada, dataFimEsperada);
    }

    @Test
    void testObterTelemetria_ListaVazia() {
        // Arrange
        when(telemetriaService.obterMetricasPorPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(Arrays.asList());

        // Act
        ResponseEntity<Map<String, Object>> response = telemetriaController.obterTelemetria("2025-10-01", "2025-10-31");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        
        List<TelemetriaService.MetricaServico> servicosResponse = 
            (List<TelemetriaService.MetricaServico>) body.get("servicos");
        assertTrue(servicosResponse.isEmpty());
        
        Map<String, String> periodo = (Map<String, String>) body.get("periodo");
        assertEquals("2025-10-01", periodo.get("inicio"));
        assertEquals("2025-10-31", periodo.get("fim"));
    }

    @Test
    void testObterTelemetria_MultiplosServicos() {
        // Arrange
        TelemetriaService.MetricaServico metrica1 = 
            new TelemetriaService.MetricaServico("simular-investimento", 120, 250L);
        TelemetriaService.MetricaServico metrica2 = 
            new TelemetriaService.MetricaServico("perfil-risco", 80, 180L);
        TelemetriaService.MetricaServico metrica3 = 
            new TelemetriaService.MetricaServico("simulacoes", 60, 120L);
        TelemetriaService.MetricaServico metrica4 = 
            new TelemetriaService.MetricaServico("produtos-recomendados", 40, 90L);
        
        List<TelemetriaService.MetricaServico> metricas = Arrays.asList(metrica1, metrica2, metrica3, metrica4);
        
        when(telemetriaService.obterMetricasPorPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(metricas);

        // Act
        ResponseEntity<Map<String, Object>> response = telemetriaController.obterTelemetria("2025-10-01", "2025-10-31");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        
        List<TelemetriaService.MetricaServico> servicosResponse = 
            (List<TelemetriaService.MetricaServico>) body.get("servicos");
        assertEquals(4, servicosResponse.size());
        
        // Verificar todos os serviços
        assertEquals("simular-investimento", servicosResponse.get(0).getNomeServico());
        assertEquals("perfil-risco", servicosResponse.get(1).getNomeServico());
        assertEquals("simulacoes", servicosResponse.get(2).getNomeServico());
        assertEquals("produtos-recomendados", servicosResponse.get(3).getNomeServico());
    }

    @Test
    void testObterTelemetria_ComDatasDiferentes() {
        // Arrange
        TelemetriaService.MetricaServico metrica = 
            new TelemetriaService.MetricaServico("simular-investimento", 50, 200L);
        
        when(telemetriaService.obterMetricasPorPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(Arrays.asList(metrica));

        // Testar diferentes combinações de datas
        String[][] datasTeste = {
            {"2025-01-01", "2025-01-31"},
            {"2025-03-15", "2025-03-20"},
            {"2025-12-01", "2025-12-31"},
            {"2024-10-01", "2024-10-31"}
        };

        for (String[] datas : datasTeste) {
            String inicio = datas[0];
            String fim = datas[1];
            
            // Act
            ResponseEntity<Map<String, Object>> response = telemetriaController.obterTelemetria(inicio, fim);

            // Assert
            assertEquals(200, response.getStatusCodeValue());
            
            Map<String, Object> body = response.getBody();
            Map<String, String> periodo = (Map<String, String>) body.get("periodo");
            assertEquals(inicio, periodo.get("inicio"));
            assertEquals(fim, periodo.get("fim"));
            
            // Verificar conversão de datas
            LocalDateTime dataInicioEsperada = LocalDate.parse(inicio).atStartOfDay();
            LocalDateTime dataFimEsperada = LocalDate.parse(fim).atTime(LocalTime.MAX);
            verify(telemetriaService, atLeastOnce()).obterMetricasPorPeriodo(dataInicioEsperada, dataFimEsperada);
        }
    }

    @Test
    void testObterTelemetria_EstruturaResposta() {
        // Arrange
        TelemetriaService.MetricaServico metrica = 
            new TelemetriaService.MetricaServico("teste-servico", 100, 300L);
        
        when(telemetriaService.obterMetricasPorPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(Arrays.asList(metrica));

        // Act
        ResponseEntity<Map<String, Object>> response = telemetriaController.obterTelemetria("2025-10-01", "2025-10-31");

        // Assert - Verificar estrutura completa da resposta
        assertEquals(200, response.getStatusCodeValue());
        
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.containsKey("servicos"));
        assertTrue(body.containsKey("periodo"));
        
        // Verificar estrutura do período
        Map<String, String> periodo = (Map<String, String>) body.get("periodo");
        assertTrue(periodo.containsKey("inicio"));
        assertTrue(periodo.containsKey("fim"));
        assertEquals("2025-10-01", periodo.get("inicio"));
        assertEquals("2025-10-31", periodo.get("fim"));
        
        // Verificar estrutura dos serviços
        List<TelemetriaService.MetricaServico> servicos = 
            (List<TelemetriaService.MetricaServico>) body.get("servicos");
        assertEquals(1, servicos.size());
        
        TelemetriaService.MetricaServico servico = servicos.get(0);
        assertEquals("teste-servico", servico.getNomeServico());
        assertEquals(100, servico.getQuantidadeChamadas());
        assertEquals(300L, servico.getMediaTempoRespostaMs());
    }

    @Test
    void testObterTelemetria_ConversaoDatasCorreta() {
        // Arrange
        when(telemetriaService.obterMetricasPorPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(Arrays.asList());

        // Act
        telemetriaController.obterTelemetria("2025-10-01", "2025-10-31");

        // Assert - Verificar conversão exata das datas
        LocalDateTime dataInicioEsperada = LocalDate.of(2025, 10, 1).atStartOfDay();
        LocalDateTime dataFimEsperada = LocalDate.of(2025, 10, 31).atTime(23, 59, 59, 999999999);
        
        verify(telemetriaService).obterMetricasPorPeriodo(dataInicioEsperada, dataFimEsperada);
    }

    @Test
    void testObterTelemetria_MetricasComValoresExtremos() {
        // Arrange - Testar com valores extremos
        TelemetriaService.MetricaServico metricaAlta = 
            new TelemetriaService.MetricaServico("servico-pesado", 1000, 5000L);
        TelemetriaService.MetricaServico metricaBaixa = 
            new TelemetriaService.MetricaServico("servico-leve", 5, 10L);
        TelemetriaService.MetricaServico metricaZero = 
            new TelemetriaService.MetricaServico("servico-zero", 0, 0L);
        
        List<TelemetriaService.MetricaServico> metricas = Arrays.asList(metricaAlta, metricaBaixa, metricaZero);
        
        when(telemetriaService.obterMetricasPorPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(metricas);

        // Act
        ResponseEntity<Map<String, Object>> response = telemetriaController.obterTelemetria("2025-10-01", "2025-10-31");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        
        Map<String, Object> body = response.getBody();
        List<TelemetriaService.MetricaServico> servicosResponse = 
            (List<TelemetriaService.MetricaServico>) body.get("servicos");
        
        assertEquals(3, servicosResponse.size());
        
        // Verificar valores extremos
        assertEquals(1000, servicosResponse.get(0).getQuantidadeChamadas());
        assertEquals(5000L, servicosResponse.get(0).getMediaTempoRespostaMs());
        
        assertEquals(5, servicosResponse.get(1).getQuantidadeChamadas());
        assertEquals(10L, servicosResponse.get(1).getMediaTempoRespostaMs());
        
        assertEquals(0, servicosResponse.get(2).getQuantidadeChamadas());
        assertEquals(0L, servicosResponse.get(2).getMediaTempoRespostaMs());
    }
}