package br.com.farmacia.service;

import br.com.farmacia.models.Estoque;
import br.com.farmacia.repository.EstoqueRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    public void save(Estoque estoque) {
        this.estoqueRepository.save(estoque);
    }

    public void deleteByid(Long id) {
        this.estoqueRepository.deleteById(id);
    }

    public Optional<Estoque> findById(Long id) {
        return this.estoqueRepository.findById(id);
    }

    public List<Estoque> findAll() {
        return this.estoqueRepository.findAll();
    }

    public Estoque getById(Long id) {
        return this.estoqueRepository.findById(id).get();
    }

    public void salvarEstoque(Estoque estoque) {
        estoqueRepository.save(estoque);
    }

}
