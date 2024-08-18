package br.com.farmacia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.farmacia.models.Farmaceutico;
import br.com.farmacia.models.Gerente;
import br.com.farmacia.repository.FuncionarioRepository;
import br.com.farmacia.service.FuncionarioService;

@SpringBootTest
public class FuncionarioServiceTest {

    @Autowired
    private FuncionarioService funcionarioService;

    @MockBean
    private FuncionarioRepository funcionarioRepository;

    @Test
    public void testRegistrarFarmaceutico() {
        Farmaceutico farmaceutico = new Farmaceutico();
        farmaceutico.setNome("João");
        farmaceutico.setEmail("joao@email.com");
        farmaceutico.setCpf("12345678900");
        farmaceutico.setSalario(new BigDecimal("3000.00"));
        farmaceutico.setDataAdmissao(LocalDate.now());
        farmaceutico.setCrf("123456");

        Mockito.when(funcionarioRepository.save(Mockito.any(Farmaceutico.class))).thenReturn(farmaceutico);

        Farmaceutico savedFarmaceutico = (Farmaceutico) funcionarioService.registrarFuncionario(farmaceutico);

        assertNotNull(savedFarmaceutico);
        assertEquals("João", savedFarmaceutico.getNome());
    }

    @Test
    public void testRegistrarGerente() {
        Gerente gerente = new Gerente();
        gerente.setNome("Maria");
        gerente.setEmail("maria@email.com");
        gerente.setCpf("98765432100");
        gerente.setSalario(new BigDecimal("5000.00"));
        gerente.setDataAdmissao(LocalDate.now());
        gerente.setDepartamento("Vendas");

        Mockito.when(funcionarioRepository.save(Mockito.any(Gerente.class))).thenReturn(gerente);

        Gerente savedGerente = (Gerente) funcionarioService.registrarFuncionario(gerente);

        assertNotNull(savedGerente);
        assertEquals("Maria", savedGerente.getNome());
    }

    @Test
    public void testEditarFarmaceutico() {
        Farmaceutico farmaceutico = new Farmaceutico();
        farmaceutico.setId(1L);
        farmaceutico.setNome("João");

        Mockito.when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(farmaceutico));
        Mockito.when(funcionarioRepository.save(farmaceutico)).thenReturn(farmaceutico);

        Farmaceutico funcionarioAtualizado = (Farmaceutico) funcionarioService.editarFuncionario(farmaceutico);

        assertEquals("João", funcionarioAtualizado.getNome());
    }

    @Test
    public void testEditarGerente() {
        Gerente gerente = new Gerente();
        gerente.setId(2L);
        gerente.setNome("Maria");

        Mockito.when(funcionarioRepository.findById(2L)).thenReturn(Optional.of(gerente));
        Mockito.when(funcionarioRepository.save(gerente)).thenReturn(gerente);

        Gerente gerenteAtualizado = (Gerente) funcionarioService.editarFuncionario(gerente);

        assertEquals("Maria", gerenteAtualizado.getNome());
    }

    @Test
    public void testRemoverFarmaceutico() {
        Farmaceutico farmaceutico = new Farmaceutico();
        farmaceutico.setId(1L);

        Mockito.when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(farmaceutico));
        Mockito.doNothing().when(funcionarioRepository).delete(farmaceutico);

        funcionarioService.removerFuncionario(1L);

        Mockito.verify(funcionarioRepository, Mockito.times(1)).delete(farmaceutico);
    }

    @Test
    public void testRemoverGerente() {
        Gerente gerente = new Gerente();
        gerente.setId(2L);

        Mockito.when(funcionarioRepository.findById(2L)).thenReturn(Optional.of(gerente));
        Mockito.doNothing().when(funcionarioRepository).delete(gerente);

        funcionarioService.removerFuncionario(2L);

        Mockito.verify(funcionarioRepository, Mockito.times(1)).delete(gerente);
    }

}
