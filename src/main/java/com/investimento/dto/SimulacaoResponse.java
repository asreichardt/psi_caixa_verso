package com.investimento.dto;

import java.time.LocalDateTime;

public class SimulacaoResponse {
    private ProdutoResponse produtoValidado;
    private ResultadoSimulacao resultadoSimulacao;
    private LocalDateTime dataSimulacao;
    
    public SimulacaoResponse() {}
    
    public SimulacaoResponse(ProdutoResponse produtoValidado, ResultadoSimulacao resultadoSimulacao) {
        this.produtoValidado = produtoValidado;
        this.resultadoSimulacao = resultadoSimulacao;
        this.dataSimulacao = LocalDateTime.now();
    }
    
    public ProdutoResponse getProdutoValidado() { return produtoValidado; }
    public void setProdutoValidado(ProdutoResponse produtoValidado) { this.produtoValidado = produtoValidado; }
    
    public ResultadoSimulacao getResultadoSimulacao() { return resultadoSimulacao; }
    public void setResultadoSimulacao(ResultadoSimulacao resultadoSimulacao) { this.resultadoSimulacao = resultadoSimulacao; }
    
    public LocalDateTime getDataSimulacao() { return dataSimulacao; }
    public void setDataSimulacao(LocalDateTime dataSimulacao) { this.dataSimulacao = dataSimulacao; }
}