package br.com.farmacia.services;

import br.com.farmacia.models.Cliente;
import br.com.farmacia.service.ClienteService;
import br.com.farmacia.repository.ClienteRepository;
import br.com.farmacia.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setEmail("joao@example.com");
        cliente.setCpf("123.456.789-00");
    }

    @Test
    public void deveCriarCliente() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        Cliente novoCliente = clienteService.criarCliente(cliente);
        assertNotNull(novoCliente);
        assertEquals(cliente.getNome(), novoCliente.getNome());
    }

    @Test
    public void deveAtualizarCliente() {
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        Cliente clienteAtualizado = clienteService.atualizarCliente(cliente.getId(), cliente);
        assertNotNull(clienteAtualizado);
        assertEquals(cliente.getNome(), clienteAtualizado.getNome());
    }

    @Test
    public void deveLancarExcecaoSeClienteNaoExistirParaAtualizar() {
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> clienteService.atualizarCliente(cliente.getId(), cliente));
    }

    @Test
    public void deveRemoverCliente() {
        when(clienteRepository.existsById(cliente.getId())).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(cliente.getId());
        assertDoesNotThrow(() -> clienteService.removerCliente(cliente.getId()));
    }

    @Test
    public void deveLancarExcecaoSeClienteNaoExistirParaRemover() {
        when(clienteRepository.existsById(cliente.getId())).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> clienteService.removerCliente(cliente.getId()));
    }
}