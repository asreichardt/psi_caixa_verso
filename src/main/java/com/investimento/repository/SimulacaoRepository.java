package com.investimento.repository;

import com.investimento.entity.Simulacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SimulacaoRepository extends JpaRepository<Simulacao, Long> {
    
    List<Simulacao> findByClienteId(Long clienteId);
    
    @Query("SELECT s FROM Simulacao s WHERE DATE(s.dataSimulacao) = :data")
    List<Simulacao> findByDataSimulacao(LocalDate data);
    
    @Query("SELECT s.produto, DATE(s.dataSimulacao), COUNT(s), AVG(s.valorFinal) " +
           "FROM Simulacao s " +
           "WHERE DATE(s.dataSimulacao) BETWEEN :dataInicio AND :dataFim " +
           "GROUP BY s.produto, DATE(s.dataSimulacao)")
    List<Object[]> findSimulacoesAgrupadasPorProdutoEDia(LocalDate dataInicio, LocalDate dataFim);
}