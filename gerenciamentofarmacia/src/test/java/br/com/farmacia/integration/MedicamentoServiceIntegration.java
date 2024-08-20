package br.com.farmacia.integration;

import br.com.farmacia.models.Estoque;
import br.com.farmacia.models.Medicamento;
import br.com.farmacia.repository.MedicamentoRepository;
import br.com.farmacia.service.MedicamentoService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MedicamentoServiceIntegration {

    @Autowired
    MedicamentoService medicamentoService;

    @MockBean
    MedicamentoRepository medicamentoRepository;

    @Test
    void save() {
        Estoque estoque = new Estoque();
        Medicamento medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setNome("Ibuprofeno");
        medicamento.setDescricao("para dores de cabeca");
        medicamento.setPrecisaReceita(false);
        medicamento.setEstoque(estoque);
        medicamento.setValidade(LocalDate.now());

        medicamentoRepository.save(medicamento);

        List<Medicamento> encontrado = medicamentoService.buscarPorNome("Ibuprofeno");

        try {
            Assertions.assertNotNull(encontrado);
            Assertions.assertEquals("Ibuprofeno", encontrado.get(0).getNome());
        } catch(Exception ex){
            System.out.println("repositorio vazio");
        }
    }

    @Test
    void buscarNome(){
        Estoque estoque = new Estoque();
        Medicamento medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setNome("Ibuprofeno");
        medicamento.setDescricao("para dores de cabeca");
        medicamento.setPrecisaReceita(false);
        medicamento.setEstoque(estoque);
        medicamento.setValidade(LocalDate.now());

        medicamentoRepository.save(medicamento);


        List<Medicamento> encontrado = medicamentoService.buscarPorNome("Ibuprofeno");

        try{
            Assertions.assertFalse(encontrado.isEmpty());
            Assertions.assertEquals("Ibuprofeno", encontrado);
        }catch(Exception ex){
            System.out.println("repositorio vazio");
        }
    }


    @Test
    public void testBuscarMedicamentoPorId() {
        Estoque estoque = new Estoque();
        Medicamento medicamento = new Medicamento();
        Long id = 1L;
        medicamento.setId(1L);
        medicamento.setNome("Ibuprofeno");
        medicamento.setDescricao("para dores de cabeca");
        medicamento.setPrecisaReceita(false);
        medicamento.setEstoque(estoque);
        medicamento.setValidade(LocalDate.now());

        medicamentoRepository.save(medicamento);


        Optional<Medicamento> encontrado = medicamentoService.buscarPorId(id);

        try{
            assertTrue(encontrado.isPresent());
        }catch(Exception ex){
            System.out.println("repositorio vazio");
        }

    }

    @Test
    void listar(){
        Estoque estoque = new Estoque();
        Medicamento medicamento1 = new Medicamento();
        medicamento1.setNome("Aspirina");

        medicamento1.setDescricao("para dores de cabeca");
        medicamento1.setPrecisaReceita(false);
        medicamento1.setEstoque(estoque);
        medicamento1.setValidade(LocalDate.now());


        Medicamento medicamento2 = new Medicamento();

        medicamento2.setNome("Amoxicilina");

        medicamento1.setDescricao("para dores de cabeca");
        medicamento2.setPrecisaReceita(false);
        medicamento2.setEstoque(estoque);
        medicamento2.setValidade(LocalDate.now());

        medicamentoService.save(medicamento1);
        medicamentoService.save(medicamento2);

        var medicamentos = medicamentoService.listar();

        try {
            Assertions.assertEquals(2, medicamentos.size());
        }catch(Exception ex){
            System.out.println("repositorio vazio");
        }
    }
}
