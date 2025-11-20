package com.investimento.controller;

import com.investimento.dto.SimulacaoRequest;
import com.investimento.dto.SimulacaoResponse;
import com.investimento.entity.Simulacao;
import com.investimento.service.SimulacaoService;
import com.investimento.service.TelemetriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SimulacaoController {
    
    @Autowired
    private SimulacaoService simulacaoService;
    
    @Autowired
    private TelemetriaService telemetriaService;
    
    @PostMapping("/simular-investimento")
    public ResponseEntity<SimulacaoResponse> simularInvestimento(@RequestBody SimulacaoRequest request) {
        long inicio = System.currentTimeMillis();
        
        try {
            SimulacaoResponse response = simulacaoService.simularInvestimento(request);
            long tempoResposta = System.currentTimeMillis() - inicio;
            telemetriaService.registrarChamada("simular-investimento", tempoResposta, true);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            long tempoResposta = System.currentTimeMillis() - inicio;
            telemetriaService.registrarChamada("simular-investimento", tempoResposta, false);
            throw e;
        }
    }
    
    @GetMapping("/simulacoes")
    public ResponseEntity<List<Simulacao>> obterTodasSimulacoes() {
        long inicio = System.currentTimeMillis();
        
        try {
            List<Simulacao> simulacoes = simulacaoService.obterTodasSimulacoes();
            long tempoResposta = System.currentTimeMillis() - inicio;
            telemetriaService.registrarChamada("simulacoes", tempoResposta, true);
            
            return ResponseEntity.ok(simulacoes);
        } catch (Exception e) {
            long tempoResposta = System.currentTimeMillis() - inicio;
            telemetriaService.registrarChamada("simulacoes", tempoResposta, false);
            throw e;
        }
    }
    
    @GetMapping("/simulacoes/{clienteId}")
    public ResponseEntity<List<Simulacao>> obterSimulacoesPorCliente(@PathVariable Long clienteId) {
        List<Simulacao> simulacoes = simulacaoService.obterSimulacoesPorCliente(clienteId);
        return ResponseEntity.ok(simulacoes);
    }
}