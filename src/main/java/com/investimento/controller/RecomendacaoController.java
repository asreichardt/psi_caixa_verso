package com.investimento.controller;

import com.investimento.entity.Produto;
import com.investimento.service.MotorRecomendacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RecomendacaoController {
    
    @Autowired
    private MotorRecomendacaoService motorRecomendacaoService;
    
    @GetMapping("/perfil-risco/{clienteId}")
    public ResponseEntity<Map<String, Object>> determinarPerfilRisco(@PathVariable Long clienteId) {
        Double volumeInvestimento = 25000.0;
        Integer frequenciaMovimentacao = 3;
        Boolean preferenciaLiquidez = true;
        
        String perfil = motorRecomendacaoService.determinarPerfilRisco(
            volumeInvestimento, frequenciaMovimentacao, preferenciaLiquidez);
        
        Map<String, Object> response = new HashMap<>();
        response.put("clienteId", clienteId);
        response.put("perfil", perfil);
        response.put("pontuacao", calcularPontuacao(volumeInvestimento, frequenciaMovimentacao, preferenciaLiquidez));
        response.put("descricao", obterDescricaoPerfil(perfil));
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/produtos-recomendados/{perfil}")
    public ResponseEntity<List<Produto>> obterProdutosRecomendados(@PathVariable String perfil) {
        List<Produto> produtos = motorRecomendacaoService.recomendarProdutos(perfil);
        return ResponseEntity.ok(produtos);
    }
    
    private int calcularPontuacao(Double volume, Integer frequencia, Boolean liquidez) {
        int pontuacao = 0;
        if (volume > 50000) pontuacao += 30;
        else if (volume > 20000) pontuacao += 20;
        else pontuacao += 10;
        
        if (frequencia > 10) pontuacao += 40;
        else if (frequencia > 5) pontuacao += 25;
        else pontuacao += 15;
        
        if (!liquidez) pontuacao += 30;
        else pontuacao += 10;
        
        return pontuacao;
    }
    
    private String obterDescricaoPerfil(String perfil) {
        switch (perfil.toUpperCase()) {
            case "CONSERVADOR":
                return "Perfil com baixa tolerância a risco, foco em segurança e liquidez";
            case "MODERADO":
                return "Perfil equilibrado entre segurança e rentabilidade";
            case "AGRESSIVO":
                return "Perfil com alta tolerância a risco, busca máxima rentabilidade";
            default:
                return "Perfil não identificado";
        }
    }
}