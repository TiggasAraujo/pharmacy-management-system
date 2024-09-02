package br.com.farmacia.repository;

import br.com.farmacia.models.Cliente;
import br.com.farmacia.models.Venda;
import br.com.farmacia.models.Vendedor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByClienteId(Long clienteId);

    List<Venda> findByCliente(Cliente cliente);

    List<Venda> findByVendedor(Vendedor vendedor); 
}
