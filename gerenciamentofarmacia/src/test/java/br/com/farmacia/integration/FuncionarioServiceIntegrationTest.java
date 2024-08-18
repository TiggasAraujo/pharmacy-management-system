package br.com.farmacia.integration;

import br.com.farmacia.models.Farmaceutico;
import br.com.farmacia.models.Gerente;
import br.com.farmacia.service.FuncionarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class FuncionarioServiceIntegrationTest {

    @Autowired
    private FuncionarioService funcionarioService;

    @Test
    public void testRegistrarFarmaceutico() {
        Farmaceutico farmaceutico = new Farmaceutico();
        farmaceutico.setNome("Ana");
        farmaceutico.setEmail("ana@email.com");
        farmaceutico.setCpf("98765432100");
        farmaceutico.setSalario(new BigDecimal("4000.00"));
        farmaceutico.setDataAdmissao(LocalDate.now());

        Farmaceutico savedFarmaceutico = (Farmaceutico) funcionarioService.registrarFuncionario(farmaceutico);

        assertNotNull(savedFarmaceutico);
        assertEquals("Ana", savedFarmaceutico.getNome());
    }

    @Test
    public void testEditarFarmaceutico() {
        Farmaceutico farmaceutico = new Farmaceutico();
        farmaceutico.setNome("Carlos");
        farmaceutico.setEmail("carlos@email.com");
        farmaceutico.setCpf("12345678911");
        farmaceutico.setSalario(new BigDecimal("5000.00"));
        farmaceutico.setDataAdmissao(LocalDate.now());

        Farmaceutico savedFarmaceutico = (Farmaceutico) funcionarioService.registrarFuncionario(farmaceutico);

        savedFarmaceutico.setNome("Carlos Silva");

        Farmaceutico updatedFarmaceutico = (Farmaceutico) funcionarioService.editarFuncionario(savedFarmaceutico);

        assertNotNull(updatedFarmaceutico);
        assertEquals("Carlos Silva", updatedFarmaceutico.getNome());
    }

    @Test
    public void testRemoverFarmaceutico() {
        Farmaceutico farmaceutico = new Farmaceutico();
        farmaceutico.setNome("Paulo");
        farmaceutico.setEmail("paulo@email.com");
        farmaceutico.setCpf("11122233344");
        farmaceutico.setSalario(new BigDecimal("4500.00"));
        farmaceutico.setDataAdmissao(LocalDate.now());

        Farmaceutico savedFarmaceutico = (Farmaceutico) funcionarioService.registrarFuncionario(farmaceutico);

        funcionarioService.removerFuncionario(savedFarmaceutico.getId());

        Optional<Farmaceutico> deletedFarmaceutico = funcionarioService.buscarFuncionarioPorId(savedFarmaceutico.getId())
            .map(f -> (Farmaceutico) f);

        assertFalse(deletedFarmaceutico.isPresent());
    }

    @Test
    public void testRegistrarGerente() {
        Gerente gerente = new Gerente();
        gerente.setNome("Mariana");
        gerente.setEmail("mariana@email.com");
        gerente.setCpf("98765432122");
        gerente.setSalario(new BigDecimal("8000.00"));
        gerente.setDataAdmissao(LocalDate.now());

        Gerente savedGerente = (Gerente) funcionarioService.registrarFuncionario(gerente);

        assertNotNull(savedGerente);
        assertEquals("Mariana", savedGerente.getNome());
    }

    @Test
    public void testEditarGerente() {
        Gerente gerente = new Gerente();
        gerente.setNome("Lucas");
        gerente.setEmail("lucas@email.com");
        gerente.setCpf("12345678933");
        gerente.setSalario(new BigDecimal("8500.00"));
        gerente.setDataAdmissao(LocalDate.now());

        Gerente savedGerente = (Gerente) funcionarioService.registrarFuncionario(gerente);

        savedGerente.setNome("Lucas Almeida");

        Gerente updatedGerente = (Gerente) funcionarioService.editarFuncionario(savedGerente);

        assertNotNull(updatedGerente);
        assertEquals("Lucas Almeida", updatedGerente.getNome());
    }
}
