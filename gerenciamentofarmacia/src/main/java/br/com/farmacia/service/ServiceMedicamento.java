package br.com.farmacia.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import Repository.MedicamentoRepository;
import br.com.farmacia.models.Medicamento;
import org.springframework.stereotype.Service;

public class ServiceMedicamento {
	
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
    public List<Medicamento> findByVencimento() {
        return this.medicamentoRepository.findByVencimento();
    }
}
