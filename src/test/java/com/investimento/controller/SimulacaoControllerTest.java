package com.investimento.controller;

import com.investimento.dto.SimulacaoRequest;
import com.investimento.dto.SimulacaoResponse;
import com.investimento.dto.ProdutoResponse;
import com.investimento.dto.ResultadoSimulacao;
import com.investimento.entity.Simulacao;
import com.investimento.service.SimulacaoService;
import com.investimento.service.TelemetriaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SimulacaoControllerTest {

    @Mock
    private SimulacaoService simulacaoService;

    @Mock
    private TelemetriaService telemetriaService;

    @InjectMocks
    private SimulacaoController simulacaoController;

    @Test
    void testSimularInvestimento_Sucesso() {
        // Arrange
        SimulacaoRequest request = new SimulacaoRequest(123L, 10000.00, 12, "CDB");

        ProdutoResponse produtoResponse = new ProdutoResponse(1L, "CDB Banco ABC", "CDB", 0.12, "Baixo");
        ResultadoSimulacao resultado = new ResultadoSimulacao(11200.00, 0.12, 12);
        SimulacaoResponse expectedResponse = new SimulacaoResponse(produtoResponse, resultado);

        when(simulacaoService.simularInvestimento(request)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<SimulacaoResponse> response = simulacaoController.simularInvestimento(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
        verify(simulacaoService).simularInvestimento(request);
        verify(telemetriaService).registrarChamada(eq("simular-investimento"), anyLong(), eq(true));
    }

    @Test
    void testSimularInvestimento_Erro() {
        // Arrange
        SimulacaoRequest request = new SimulacaoRequest(123L, 10000.00, 12, "CDB");
        when(simulacaoService.simularInvestimento(request))
                .thenThrow(new RuntimeException("Erro na simulação"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            simulacaoController.simularInvestimento(request);
        });

        verify(telemetriaService).registrarChamada(eq("simular-investimento"), anyLong(), eq(false));
    }

    @Test
    void testObterTodasSimulacoes_Sucesso() {
        // Arrange
        Simulacao simulacao1 = new Simulacao(123L, "CDB Banco ABC", 10000.00, 11200.00, 12, "CDB");
        Simulacao simulacao2 = new Simulacao(456L, "Fundo XPTO", 5000.00, 5600.00, 6, "Fundo");
        List<Simulacao> simulacoes = Arrays.asList(simulacao1, simulacao2);

        when(simulacaoService.obterTodasSimulacoes()).thenReturn(simulacoes);

        // Act
        ResponseEntity<List<Simulacao>> response = simulacaoController.obterTodasSimulacoes();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals(simulacoes, response.getBody());
        verify(telemetriaService).registrarChamada(eq("simulacoes"), anyLong(), eq(true));
    }

    @Test
    void testObterTodasSimulacoes_ListaVazia() {
        // Arrange
        when(simulacaoService.obterTodasSimulacoes()).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<List<Simulacao>> response = simulacaoController.obterTodasSimulacoes();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isEmpty());
        verify(telemetriaService).registrarChamada(eq("simulacoes"), anyLong(), eq(true));
    }

    @Test
    void testObterTodasSimulacoes_Erro() {
        // Arrange
        when(simulacaoService.obterTodasSimulacoes())
                .thenThrow(new RuntimeException("Erro no banco"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            simulacaoController.obterTodasSimulacoes();
        });

        verify(telemetriaService).registrarChamada(eq("simulacoes"), anyLong(), eq(false));
    }

    @Test
    void testObterSimulacoesPorCliente_Sucesso() {
        // Arrange
        Simulacao simulacao1 = new Simulacao(123L, "CDB Banco ABC", 10000.00, 11200.00, 12, "CDB");
        Simulacao simulacao2 = new Simulacao(123L, "Tesouro Direto", 8000.00, 8480.00, 12, "Tesouro");
        List<Simulacao> simulacoes = Arrays.asList(simulacao1, simulacao2);

        when(simulacaoService.obterSimulacoesPorCliente(123L)).thenReturn(simulacoes);

        // Act
        ResponseEntity<List<Simulacao>> response = simulacaoController.obterSimulacoesPorCliente(123L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals(123L, response.getBody().get(0).getClienteId());
        assertEquals(123L, response.getBody().get(1).getClienteId());
        verify(simulacaoService).obterSimulacoesPorCliente(123L);
    }

    @Test
    void testObterSimulacoesPorCliente_NenhumaEncontrada() {
        // Arrange
        when(simulacaoService.obterSimulacoesPorCliente(999L)).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<List<Simulacao>> response = simulacaoController.obterSimulacoesPorCliente(999L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isEmpty());
        verify(simulacaoService).obterSimulacoesPorCliente(999L);
    }
}