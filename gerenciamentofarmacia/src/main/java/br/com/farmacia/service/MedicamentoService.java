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

    public List<Medicamento> findAll() {
        return medicamentoRepository.findAll();
    }

    public void save(Medicamento medicamento) {
        medicamentoRepository.save(medicamento);
    }

    public Medicamento getById(Long id) {
        return medicamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado"));
    }

    public void deleteById(Long id) {
        medicamentoRepository.deleteById(id);
    }

    public List<Medicamento> buscarPorNome(String nome) {
        return medicamentoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<Medicamento> buscarPorId(Long id) {
        return medicamentoRepository.findById(id);
    }

    //Refactoring remove dead code
    //Os dois métodos a seguir eram redundantes, pois, no fim, retornavam todos os medicamentos, tal qual o método findAll
    /*public List<Medicamento> listar() {
        return medicamentoRepository.findAll();
    }


    public List<Medicamento> buscarTodosMedicamentos() {
        return medicamentoRepository.findAll();

    }*/

}
