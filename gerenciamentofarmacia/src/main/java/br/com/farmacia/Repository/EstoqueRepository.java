package br.com.farmacia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.farmacia.models.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    
}
