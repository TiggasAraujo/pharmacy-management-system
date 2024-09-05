package br.com.farmacia.services;


import br.com.farmacia.models.Cliente;
import br.com.farmacia.models.ItemVenda;
import br.com.farmacia.models.Medicamento;
import br.com.farmacia.models.Venda;
import br.com.farmacia.models.Vendedor;
import br.com.farmacia.repository.VendaRepository;
import br.com.farmacia.service.MedicamentoService;
import br.com.farmacia.service.VendaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
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

    @Test
    void excluirVenda() {
        vendaService.excluirVenda(1L);
        verify(vendaRepository, times(1)).deleteById(1L);
    }

    @Test
    void buscarTodasVendas() {
        Venda venda1 = new Venda();
        Venda venda2 = new Venda();
        List<Venda> vendas = Arrays.asList(venda1, venda2);
        
        when(vendaRepository.findAll()).thenReturn(vendas);

        List<Venda> resultado = vendaService.buscarTodasVendas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    void buscarVendasPorCliente() {
        Cliente cliente = new Cliente();
        Venda venda = new Venda();
        venda.setCliente(cliente);
        List<Venda> vendas = Arrays.asList(venda);
        
        when(vendaRepository.findByCliente(cliente)).thenReturn(vendas);

        List<Venda> resultado = vendaService.buscarVendasPorCliente(cliente);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(cliente, resultado.get(0).getCliente());
    }

    @Test
    void buscarVendasPorVendedor() {
        Vendedor vendedor = new Vendedor();
        Venda venda = new Venda();
        venda.setVendedor(vendedor);
        List<Venda> vendas = Arrays.asList(venda);
        
        when(vendaRepository.findByVendedor(vendedor)).thenReturn(vendas);

        List<Venda> resultado = vendaService.buscarVendasPorVendedor(vendedor);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(vendedor, resultado.get(0).getVendedor());
    }
}