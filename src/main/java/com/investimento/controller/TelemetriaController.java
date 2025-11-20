package com.investimento.controller;

import com.investimento.service.TelemetriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TelemetriaController {
    
    @Autowired
    private TelemetriaService telemetriaService;
    
    @GetMapping("/telemetria")
    public ResponseEntity<Map<String, Object>> obterTelemetria(
            @RequestParam(defaultValue = "2025-10-01") String inicio,
            @RequestParam(defaultValue = "2025-10-31") String fim) {
        
        LocalDateTime dataInicio = LocalDate.parse(inicio).atStartOfDay();
        LocalDateTime dataFim = LocalDate.parse(fim).atTime(LocalTime.MAX);
        
        List<TelemetriaService.MetricaServico> metricas = 
            telemetriaService.obterMetricasPorPeriodo(dataInicio, dataFim);
        
        Map<String, Object> response = new HashMap<>();
        response.put("servicos", metricas);
        response.put("periodo", Map.of(
            "inicio", inicio,
            "fim", fim
        ));
        
        return ResponseEntity.ok(response);
    }
}