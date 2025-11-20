package com.investimento.repository;

import com.investimento.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    List<Produto> findByTipoAndAtivoTrue(String tipo);
    
    List<Produto> findByRiscoAndAtivoTrue(String risco);
    
    List<Produto> findByAtivoTrue();
    
    @Query("SELECT p FROM Produto p WHERE p.ativo = true AND p.valorMinimo <= :valor AND p.prazoMinimoMeses <= :prazoMeses")
    List<Produto> findProdutosValidos(Double valor, Integer prazoMeses);
}