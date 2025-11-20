package com.investimento.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "simulacao")
public class Simulacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long clienteId;
    
    @Column(nullable = false)
    private String produto;
    
    @Column(nullable = false)
    private Double valorInvestido;
    
    @Column(nullable = false)
    private Double valorFinal;
    
    @Column(nullable = false)
    private Integer prazoMeses;
    
    @Column(nullable = false)
    private LocalDateTime dataSimulacao;
    
    @Column(nullable = false)
    private String tipoProduto;
    
    public Simulacao() {}
    
    public Simulacao(Long clienteId, String produto, Double valorInvestido, 
                    Double valorFinal, Integer prazoMeses, String tipoProduto) {
        this.clienteId = clienteId;
        this.produto = produto;
        this.valorInvestido = valorInvestido;
        this.valorFinal = valorFinal;
        this.prazoMeses = prazoMeses;
        this.tipoProduto = tipoProduto;
        this.dataSimulacao = LocalDateTime.now();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    
    public String getProduto() { return produto; }
    public void setProduto(String produto) { this.produto = produto; }
    
    public Double getValorInvestido() { return valorInvestido; }
    public void setValorInvestido(Double valorInvestido) { this.valorInvestido = valorInvestido; }
    
    public Double getValorFinal() { return valorFinal; }
    public void setValorFinal(Double valorFinal) { this.valorFinal = valorFinal; }
    
    public Integer getPrazoMeses() { return prazoMeses; }
    public void setPrazoMeses(Integer prazoMeses) { this.prazoMeses = prazoMeses; }
    
    public LocalDateTime getDataSimulacao() { return dataSimulacao; }
    public void setDataSimulacao(LocalDateTime dataSimulacao) { this.dataSimulacao = dataSimulacao; }
    
    public String getTipoProduto() { return tipoProduto; }
    public void setTipoProduto(String tipoProduto) { this.tipoProduto = tipoProduto; }
}