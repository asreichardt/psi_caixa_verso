package com.investimento.controller;

import com.investimento.entity.Produto;
import com.investimento.service.MotorRecomendacaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecomendacaoControllerTest{

    @Mock
    private MotorRecomendacaoService motorRecomendacaoService;

    @InjectMocks
    private RecomendacaoController recomendacaoController;

    @Test
    void testDeterminarPerfilRisco_Conservador() {
        // Arrange
        when(motorRecomendacaoService.determinarPerfilRisco(25000.0, 3, true))
                .thenReturn("Conservador");

        // Act
        ResponseEntity<Map<String, Object>> response = recomendacaoController.determinarPerfilRisco(123L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(123L, response.getBody().get("clienteId"));
        assertEquals("Conservador", response.getBody().get("perfil"));
        assertEquals(45, response.getBody().get("pontuacao"));
        assertTrue(response.getBody().get("descricao").toString().contains("baixa tolerância"));
    }

    @Test
    void testObterProdutosRecomendados_Conservador() {
        // Arrange
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("CDB Teste");
        produto.setRisco("Baixo");

        when(motorRecomendacaoService.recomendarProdutos("Conservador"))
                .thenReturn(Arrays.asList(produto));

        // Act
        ResponseEntity<List<Produto>> response = recomendacaoController.obterProdutosRecomendados("Conservador");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("CDB Teste", response.getBody().get(0).getNome());
    }

    @Test
    void testCalculoPontuacao() {
        // Teste do método privado através do comportamento público
        when(motorRecomendacaoService.determinarPerfilRisco(25000.0, 3, true))
                .thenReturn("Conservador");

        ResponseEntity<Map<String, Object>> response = recomendacaoController.determinarPerfilRisco(123L);

        // A pontuação para 25000, 3, true deve ser 45
        assertEquals(45, response.getBody().get("pontuacao"));
    }

    @Test
    void testPerfisDiferentes() {
        // Testar diferentes retornos do serviço
        when(motorRecomendacaoService.determinarPerfilRisco(25000.0, 3, true))
                .thenReturn("Moderado");

        ResponseEntity<Map<String, Object>> response = recomendacaoController.determinarPerfilRisco(456L);

        assertEquals("Moderado", response.getBody().get("perfil"));
        assertTrue(response.getBody().get("descricao").toString().contains("equilibrado"));
    }
}