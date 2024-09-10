package br.com.farmacia.services;


import br.com.farmacia.models.Venda;
import br.com.farmacia.repository.VendaRepository;
import br.com.farmacia.service.MedicamentoService;
import br.com.farmacia.service.VendaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VendaServiceTest {

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private MedicamentoService medicamentoService;

    @InjectMocks
    private VendaService vendaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void buscarPorIdVendaExistente() {
        Venda venda = new Venda();
        venda.setId(1L);
        when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));

        Venda resultado = vendaService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void buscarPorIdVendaNaoExistente() {
        when(vendaRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            vendaService.buscarPorId(1L);
        });

        assertEquals("Venda não encontrada", exception.getMessage());
    }

}