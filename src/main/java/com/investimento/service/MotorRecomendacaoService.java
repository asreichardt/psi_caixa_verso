package com.investimento.service;

import com.investimento.entity.Produto;
import com.investimento.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MotorRecomendacaoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    public String determinarPerfilRisco(Double volumeInvestimento, Integer frequenciaMovimentacao, Boolean preferenciaLiquidez) {
        int pontuacao = 0;
        
        if (volumeInvestimento > 50000) pontuacao += 30;
        else if (volumeInvestimento > 20000) pontuacao += 20;
        else pontuacao += 10;
        
        if (frequenciaMovimentacao > 10) pontuacao += 40;
        else if (frequenciaMovimentacao > 5) pontuacao += 25;
        else pontuacao += 15;
        
        if (!preferenciaLiquidez) pontuacao += 30;
        else pontuacao += 10;
        
        if (pontuacao >= 80) return "Agressivo";
        else if (pontuacao >= 50) return "Moderado";
        else return "Conservador";
    }
    
    public List<Produto> recomendarProdutos(String perfil) {
        List<Produto> produtosAtivos = produtoRepository.findByAtivoTrue();
        
        return produtosAtivos.stream()
            .filter(p -> isProdutoCompativelComPerfil(p, perfil))
            .collect(Collectors.toList());
    }
    
    private boolean isProdutoCompativelComPerfil(Produto produto, String perfil) {
        switch (perfil.toUpperCase()) {
            case "CONSERVADOR":
                return produto.getRisco().equalsIgnoreCase("Baixo");
            case "MODERADO":
                return produto.getRisco().equalsIgnoreCase("Baixo") || 
                       produto.getRisco().equalsIgnoreCase("Médio");
            case "AGRESSIVO":
                return true;
            default:
                return produto.getRisco().equalsIgnoreCase("Baixo");
        }
    }
}