package com.investimento.repository;

import com.investimento.entity.Telemetria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TelemetriaRepository extends JpaRepository<Telemetria, Long> {
    
    @Query("SELECT t.nomeServico, COUNT(t), AVG(t.tempoRespostaMs) " +
           "FROM Telemetria t " +
           "WHERE t.dataChamada BETWEEN :inicio AND :fim " +
           "GROUP BY t.nomeServico")
    List<Object[]> findMetricasPorPeriodo(LocalDateTime inicio, LocalDateTime fim);
}