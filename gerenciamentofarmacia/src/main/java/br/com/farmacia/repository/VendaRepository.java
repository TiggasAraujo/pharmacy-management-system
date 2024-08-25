package br.com.farmacia.repository;

import br.com.farmacia.models.Venda;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {


    @org.jetbrains.annotations.Contract(pure = true)
    public static @Nullable List<Venda> findByClienteId(Long clienteId) {
        return null;
    }
}