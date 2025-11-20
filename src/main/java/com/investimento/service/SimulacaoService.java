package com.investimento.service;

import com.investimento.dto.*;
import com.investimento.entity.Produto;
import com.investimento.entity.Simulacao;
import com.investimento.repository.ProdutoRepository;
import com.investimento.repository.SimulacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimulacaoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private SimulacaoRepository simulacaoRepository;
    
    public SimulacaoResponse simularInvestimento(SimulacaoRequest request) {
        List<Produto> produtosValidos = produtoRepository.findProdutosValidos(request.getValor(), request.getPrazoMeses());
        
        List<Produto> produtosFiltrados = produtosValidos.stream()
            .filter(p -> request.getTipoProduto() == null || p.getTipo().equalsIgnoreCase(request.getTipoProduto()))
            .collect(Collectors.toList());
        
        if (produtosFiltrados.isEmpty()) {
            throw new RuntimeException("Nenhum produto encontrado para os critérios informados");
        }
        
        Produto produtoSelecionado = produtosFiltrados.get(0);
        
        Double valorFinal = calcularValorFinal(request.getValor(), produtoSelecionado.getRentabilidade(), request.getPrazoMeses());
        
        ProdutoResponse produtoResponse = new ProdutoResponse(
            produtoSelecionado.getId(),
            produtoSelecionado.getNome(),
            produtoSelecionado.getTipo(),
            produtoSelecionado.getRentabilidade(),
            produtoSelecionado.getRisco()
        );
        
        ResultadoSimulacao resultado = new ResultadoSimulacao(
            valorFinal,
            produtoSelecionado.getRentabilidade(),
            request.getPrazoMeses()
        );
        
        Simulacao simulacao = new Simulacao(
            request.getClienteId(),
            produtoSelecionado.getNome(),
            request.getValor(),
            valorFinal,
            request.getPrazoMeses(),
            produtoSelecionado.getTipo()
        );
        simulacaoRepository.save(simulacao);
        
        return new SimulacaoResponse(produtoResponse, resultado);
    }
    
    private Double calcularValorFinal(Double valorInicial, Double rentabilidade, Integer prazoMeses) {
        return valorInicial * Math.pow(1 + rentabilidade, prazoMeses / 12.0);
    }
    
    public List<Simulacao> obterTodasSimulacoes() {
        return simulacaoRepository.findAll();
    }
    
    public List<Simulacao> obterSimulacoesPorCliente(Long clienteId) {
        return simulacaoRepository.findByClienteId(clienteId);
    }
}