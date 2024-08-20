package br.com.farmacia.services;

import br.com.farmacia.models.Estoque;
import br.com.farmacia.models.Medicamento;
import br.com.farmacia.repository.MedicamentoRepository;
import br.com.farmacia.service.MedicamentoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class MedicamentoServiceTest {

    @Autowired
    MedicamentoRepository medicamentoRepository;

    @Autowired
    MedicamentoService medicamentoService;


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

        medicamentoService.save(medicamento);

        List<Medicamento> encontrado = medicamentoService.buscarPorNome("Ibuprofeno");

        try {
            Assertions.assertNotNull(encontrado);
            Assertions.assertEquals("Ibuprofeno", encontrado.get(0).getNome());
        } catch(Exception ex){
            System.out.println("repositorio vazio");
        }
    }

    @Test
    void buscarPorNome() {
        Estoque estoque = new Estoque();
        Long id = 1L;
        Medicamento medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setNome("dipirona");
        medicamento.setDescricao("para dores de cabeca");
        medicamento.setPrecisaReceita(false);
        medicamento.setEstoque(estoque);
        medicamento.setValidade(LocalDate.now());
        medicamentoService.save(medicamento);


        try{
            Optional<Medicamento> founded  = this.medicamentoRepository.findById(id);

            Assertions.assertFalse(founded.isEmpty());
            Assertions.assertEquals("Ibuprofeno", founded);
        }catch(Exception ex){
            System.out.println("nao ha medicamentos");
        }
    }

    @Test
    void buscarPorId() {
        Estoque estoque = new Estoque();
        Long id = 1L;
        Medicamento medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setNome("dipirona");
        medicamento.setDescricao("para dores de cabeca");
        medicamento.setPrecisaReceita(false);
        medicamento.setEstoque(estoque);
        medicamento.setValidade(LocalDate.now());
        medicamentoService.save(medicamento);


        try{
            Optional<Medicamento> founded  = this.medicamentoRepository.findById(id);

            Assertions.assertFalse(founded.isEmpty());
            Assertions.assertEquals("Ibuprofeno", founded);
        }catch(Exception ex){
            System.out.println("nao ha medicamentos");
        }
    }

    @Test
    void listar() {
        if(medicamentoRepository == null){
            System.out.println(" Sem resultados");
        }
        else
            medicamentoRepository.findAll();

    }
}