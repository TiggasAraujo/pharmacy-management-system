package br.com.farmacia.repository;

import br.com.farmacia.models.Venda;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByClienteId(Long clienteId);
}
