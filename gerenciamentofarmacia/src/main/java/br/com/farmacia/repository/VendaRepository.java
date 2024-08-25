package br.com.farmacia.repository;

import br.com.farmacia.models.Venda;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {


    public static List<Venda> findByClienteId(Long clienteId) {
        return null;
    }
}