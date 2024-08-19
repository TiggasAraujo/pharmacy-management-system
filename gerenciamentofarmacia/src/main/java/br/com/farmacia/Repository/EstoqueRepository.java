package br.com.farmacia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import br.com.farmacia.models.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    
    public Optional<Estoque> findByMedicamentoId(Long medicamentoId);

}