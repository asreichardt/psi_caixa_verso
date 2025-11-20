package com.investimento.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "telemetria")
public class Telemetria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nomeServico;
    
    @Column(nullable = false)
    private Long tempoRespostaMs;
    
    @Column(nullable = false)
    private LocalDateTime dataChamada;
    
    @Column(nullable = false)
    private Boolean sucesso;
    
    public Telemetria() {}
    
    public Telemetria(String nomeServico, Long tempoRespostaMs, Boolean sucesso) {
        this.nomeServico = nomeServico;
        this.tempoRespostaMs = tempoRespostaMs;
        this.sucesso = sucesso;
        this.dataChamada = LocalDateTime.now();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNomeServico() { return nomeServico; }
    public void setNomeServico(String nomeServico) { this.nomeServico = nomeServico; }
    
    public Long getTempoRespostaMs() { return tempoRespostaMs; }
    public void setTempoRespostaMs(Long tempoRespostaMs) { this.tempoRespostaMs = tempoRespostaMs; }
    
    public LocalDateTime getDataChamada() { return dataChamada; }
    public void setDataChamada(LocalDateTime dataChamada) { this.dataChamada = dataChamada; }
    
    public Boolean getSucesso() { return sucesso; }
    public void setSucesso(Boolean sucesso) { this.sucesso = sucesso; }
}