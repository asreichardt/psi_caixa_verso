package com.investimento.service;

import com.investimento.entity.Telemetria;
import com.investimento.repository.TelemetriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TelemetriaService {
    
    @Autowired
    private TelemetriaRepository telemetriaRepository;
    
    public void registrarChamada(String nomeServico, Long tempoRespostaMs, Boolean sucesso) {
        Telemetria telemetria = new Telemetria(nomeServico, tempoRespostaMs, sucesso);
        telemetriaRepository.save(telemetria);
    }
    
    public List<MetricaServico> obterMetricasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        List<Object[]> resultados = telemetriaRepository.findMetricasPorPeriodo(inicio, fim);
        List<MetricaServico> metricas = new ArrayList<>();
        
        for (Object[] resultado : resultados) {
            MetricaServico metrica = new MetricaServico(
                (String) resultado[0],
                ((Long) resultado[1]).intValue(),
                ((Number) resultado[2]).longValue()
            );
            metricas.add(metrica);
        }
        
        return metricas;
    }
    
    public static class MetricaServico {
        private String nomeServico;
        private Integer quantidadeChamadas;
        private Long mediaTempoRespostaMs;
        
        public MetricaServico(String nomeServico, Integer quantidadeChamadas, Long mediaTempoRespostaMs) {
            this.nomeServico = nomeServico;
            this.quantidadeChamadas = quantidadeChamadas;
            this.mediaTempoRespostaMs = mediaTempoRespostaMs;
        }
        
        public String getNomeServico() { return nomeServico; }
        public Integer getQuantidadeChamadas() { return quantidadeChamadas; }
        public Long getMediaTempoRespostaMs() { return mediaTempoRespostaMs; }
    }
}