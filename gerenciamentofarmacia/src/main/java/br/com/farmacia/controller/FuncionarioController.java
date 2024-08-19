package br.com.farmacia.controller;

import br.com.farmacia.models.Funcionario;
import br.com.farmacia.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<Funcionario> registrarFuncionario(@RequestBody Funcionario funcionario) {
        Funcionario novoFuncionario = funcionarioService.registrarFuncionario(funcionario);
        return new ResponseEntity<>(novoFuncionario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> editarFuncionario(@PathVariable Long id, @RequestBody Funcionario funcionario) {
        Optional<Funcionario> funcionarioExistente = funcionarioService.buscarFuncionarioPorId(id);
        if (funcionarioExistente.isPresent()) {
            funcionario.setId(id);
            Funcionario funcionarioAtualizado = funcionarioService.editarFuncionario(funcionario);
            return new ResponseEntity<>(funcionarioAtualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerFuncionario(@PathVariable Long id) {
        Optional<Funcionario> funcionarioExistente = funcionarioService.buscarFuncionarioPorId(id);
        if (funcionarioExistente.isPresent()) {
            funcionarioService.removerFuncionario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> listarFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.listarFuncionarios();
        return new ResponseEntity<>(funcionarios, HttpStatus.OK);
    }
}
