package br.com.farmacia.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.farmacia.models.Medicamento;
import br.com.farmacia.repository.MedicamentoRepository;

@Service
public class MedicamentoService {
	
	@Autowired
    private MedicamentoRepository medicamentoRepository;

    public void save(Medicamento medicamento) {
        this.medicamentoRepository.save(medicamento);
    }
    
    //busca medicamentos faltando estoque
    public List<Medicamento> findSemEstoque() {
        return this.medicamentoRepository.findSemEstoque();
    }

    ///retorna todos os medicamentos que estao no estoque
    public List<Medicamento> findByQtdEstoque() {
        return this.medicamentoRepository.findByQtdEstoque();
    }
    
    ///retorna os medicamentos de acodo com a data de validade
    // public List<Medicamento> findByVencimento() {
    //     return this.medicamentoRepository.findByVencimento();
    // }

    public List<Medicamento> buscarPorNome(String nome) {
        return medicamentoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<Medicamento> buscarPorId(Long id) {
        return medicamentoRepository.findById(id);
    }

    public List<Medicamento> listar() {
        return medicamentoRepository.findAll();
    }
}
