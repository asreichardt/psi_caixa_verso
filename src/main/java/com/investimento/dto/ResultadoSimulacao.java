package com.investimento.dto;

public class ResultadoSimulacao {
    private Double valorFinal;
    private Double rentabilidadeEfetiva;
    private Integer prazoMeses;
    
    public ResultadoSimulacao() {}
    
    public ResultadoSimulacao(Double valorFinal, Double rentabilidadeEfetiva, Integer prazoMeses) {
        this.valorFinal = valorFinal;
        this.rentabilidadeEfetiva = rentabilidadeEfetiva;
        this.prazoMeses = prazoMeses;
    }
    
    public Double getValorFinal() { return valorFinal; }
    public void setValorFinal(Double valorFinal) { this.valorFinal = valorFinal; }
    
    public Double getRentabilidadeEfetiva() { return rentabilidadeEfetiva; }
    public void setRentabilidadeEfetiva(Double rentabilidadeEfetiva) { this.rentabilidadeEfetiva = rentabilidadeEfetiva; }
    
    public Integer getPrazoMeses() { return prazoMeses; }
    public void setPrazoMeses(Integer prazoMeses) { this.prazoMeses = prazoMeses; }
}