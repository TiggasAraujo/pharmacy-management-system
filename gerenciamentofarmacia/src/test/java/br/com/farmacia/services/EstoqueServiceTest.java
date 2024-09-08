package br.com.farmacia.services;

import br.com.farmacia.models.Estoque;
import br.com.farmacia.repository.EstoqueRepository;
import br.com.farmacia.service.EstoqueService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EstoqueServiceTest {

    @Mock
    private EstoqueRepository estoqueRepository;

    @InjectMocks
    private EstoqueService estoqueService;

    @Test
    public void testSaveEstoque() {
        Estoque estoque = new Estoque();
        estoque.setQtdEstoque(100);
        estoque.setDataEntrada(LocalDate.now());

        estoqueService.save(estoque);

        Mockito.verify(estoqueRepository, Mockito.times(1)).save(estoque);
    }

    @Test
    public void testDeleteById() {
        Long estoqueId = 1L;

        estoqueService.deleteByid(estoqueId);

        Mockito.verify(estoqueRepository, Mockito.times(1)).deleteById(estoqueId);
    }

    @Test
    public void testFindById() {
        Long estoqueId = 1L;
        Estoque estoque = new Estoque();
        estoque.setId(estoqueId);

        Mockito.when(estoqueRepository.findById(estoqueId)).thenReturn(Optional.of(estoque));

        Optional<Estoque> foundEstoque = estoqueService.findById(estoqueId);

        Assertions.assertTrue(foundEstoque.isPresent());
        Assertions.assertEquals(estoqueId, foundEstoque.get().getId());
    }

    @Test
    public void testFindAll() {
        Estoque estoque1 = new Estoque();
        Estoque estoque2 = new Estoque();

        Mockito.when(estoqueRepository.findAll()).thenReturn(Arrays.asList(estoque1, estoque2));

        List<Estoque> estoques = estoqueService.findAll();

        Assertions.assertEquals(2, estoques.size());
        Mockito.verify(estoqueRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testGetById() {
        Long estoqueId = 1L;
        Estoque estoque = new Estoque();
        estoque.setId(estoqueId);

        Mockito.when(estoqueRepository.findById(estoqueId)).thenReturn(Optional.of(estoque));

        Estoque foundEstoque = estoqueService.getById(estoqueId);

        Assertions.assertNotNull(foundEstoque);
        Assertions.assertEquals(estoqueId, foundEstoque.getId());
    }
}
