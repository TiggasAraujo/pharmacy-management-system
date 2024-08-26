package br.com.farmacia.service;

import br.com.farmacia.models.Funcionario;
import br.com.farmacia.repository.FuncionarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Funcionario registrarFuncionario(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario editarFuncionario(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public void removerFuncionario(Long id) {
        funcionarioRepository.deleteById(id);
    }

    public List<Funcionario> listarFuncionarios() {
        return funcionarioRepository.findAll();
    }

    public Optional<Funcionario> buscarFuncionarioPorId(Long id) {
        return funcionarioRepository.findById(id);
    }

    //save
    public Funcionario save(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }
}
