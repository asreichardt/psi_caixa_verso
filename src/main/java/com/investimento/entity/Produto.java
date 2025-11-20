package com.investimento.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "produto")
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String tipo;
    
    @Column(nullable = false)
    private Double rentabilidade;
    
    @Column(nullable = false)
    private String risco;
    
    @Column(nullable = false)
    private Double valorMinimo;
    
    @Column(nullable = false)
    private Integer prazoMinimoMeses;
    
    @Column(nullable = false)
    private Boolean ativo;
    
    public Produto() {}
    
    public Produto(String nome, String tipo, Double rentabilidade, String risco, 
                  Double valorMinimo, Integer prazoMinimoMeses, Boolean ativo) {
        this.nome = nome;
        this.tipo = tipo;
        this.rentabilidade = rentabilidade;
        this.risco = risco;
        this.valorMinimo = valorMinimo;
        this.prazoMinimoMeses = prazoMinimoMeses;
        this.ativo = ativo;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public Double getRentabilidade() { return rentabilidade; }
    public void setRentabilidade(Double rentabilidade) { this.rentabilidade = rentabilidade; }
    
    public String getRisco() { return risco; }
    public void setRisco(String risco) { this.risco = risco; }
    
    public Double getValorMinimo() { return valorMinimo; }
    public void setValorMinimo(Double valorMinimo) { this.valorMinimo = valorMinimo; }
    
    public Integer getPrazoMinimoMeses() { return prazoMinimoMeses; }
    public void setPrazoMinimoMeses(Integer prazoMinimoMeses) { this.prazoMinimoMeses = prazoMinimoMeses; }
    
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}