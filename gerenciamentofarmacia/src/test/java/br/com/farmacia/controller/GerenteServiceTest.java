package br.com.farmacia.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import br.com.farmacia.models.Cliente;
import br.com.farmacia.models.Gerente;
import br.com.farmacia.models.Medicamento;
import br.com.farmacia.models.Promocao;
import br.com.farmacia.repository.ClienteRepository;
import br.com.farmacia.repository.GerenteRepository;
import br.com.farmacia.repository.PromocaoRepository;
import br.com.farmacia.service.GerenteService;

@ExtendWith(MockitoExtension.class)
public class GerenteServiceTest {

    @Mock
    private GerenteRepository gerenteRepository;

    @Mock
    private PromocaoRepository promocaoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private GerenteService gerenteService;

    private Gerente gerente;
    private Promocao promocao;
    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        gerente = new Gerente();
        gerente.setId(1L);

        promocao = new Promocao();
        promocao.setId(1L);
        promocao.setDescricao("Promoção Teste");
        promocao.setDesconto(0.2);
        promocao.setGerente(gerente);

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");
        cliente.setEmail("cliente@teste.com");
    }

    @Test
    public void testCriarPromocao() {
        when(gerenteRepository.findById(1L)).thenReturn(Optional.of(gerente));
        gerenteService.criarPromocao(1L, promocao);
        verify(promocaoRepository, times(1)).save(promocao);
    }

    @Test
    public void testEnviarPromocaoParaClientes() {
        Medicamento medicamento = new Medicamento();
        medicamento.setNome("Paracetamol");
        
        Promocao promocao = new Promocao();
        promocao.setMedicamento(medicamento);
        promocao.setDesconto(10.0);
        
        when(promocaoRepository.findById(1L)).thenReturn(Optional.of(promocao));
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente));
    
        gerenteService.enviarPromocaoParaClientes(1L);
    
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testFindById() {
        when(gerenteRepository.findById(1L)).thenReturn(Optional.of(gerente));
        gerenteService.findById(1L);
        verify(gerenteRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAll() {
        List<Gerente> gerentes = Arrays.asList(gerente);
        when(gerenteRepository.findAll()).thenReturn(gerentes);
        List<Gerente> result = gerenteService.findAll();
        assertEquals(gerentes, result);
    }

    @Test
    public void testSave() {
        gerenteService.save(gerente);
        verify(gerenteRepository, times(1)).save(gerente);
    }
}