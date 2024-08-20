package br.com.farmacia.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.farmacia.models.Promocao;

public interface PromocaoRepository extends JpaRepository<Promocao, Long>{

    // //findById
    // public Promocao findById(String id);
}
