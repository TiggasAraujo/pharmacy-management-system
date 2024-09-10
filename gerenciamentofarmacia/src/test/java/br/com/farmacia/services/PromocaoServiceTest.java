package br.com.farmacia.services;

import br.com.farmacia.models.Cliente;
import br.com.farmacia.models.Medicamento;
import br.com.farmacia.models.Promocao;
import br.com.farmacia.repository.ClienteRepository;
import br.com.farmacia.repository.MedicamentoRepository;
import br.com.farmacia.repository.PromocaoRepository;
import br.com.farmacia.service.PromocaoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PromocaoServiceTest {

    @Mock
    private PromocaoRepository promocaoRepository;

    @Mock
    private MedicamentoRepository medicamentoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private PromocaoService promocaoService;

    private Promocao promocao;
    private Medicamento medicamento;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criando um cliente de teste
        Cliente cliente = new Cliente();
        cliente.setNome("Raí");
        cliente.setEmail("raisanhacu@gmail.com");

        // Criando um medicamento de teste
        medicamento = new Medicamento();
        medicamento.setNome("Paracetamol");
        medicamento.setPreco(10.00);

        // Criando uma promoção de teste
        promocao = new Promocao();
        promocao.setId(1L);
        promocao.setDescricao("Promoção de Verão");
        promocao.setDataInicio(LocalDate.now());
        promocao.setDataFim(LocalDate.now().plusDays(10));
        promocao.setDesconto(0.20);  // 20% de desconto
        promocao.setMedicamento(medicamento);
    }

    // Teste para o getById
    @Test
    public void testGetById() {
        when(promocaoRepository.findById(any(Long.class))).thenReturn(Optional.of(promocao));

        Promocao promocaoRetornada = promocaoService.getById(1L);

        assertEquals(promocao, promocaoRetornada);
    }

    @Test
    public void testCriarPromocao() {
        Medicamento medicamento = new Medicamento();
        medicamento.setNome("Paracetamol");
        medicamento.setPreco(10.00);
        medicamento.setId(1L);

        Promocao promocao = new Promocao();
        promocao.setId(1L);
        promocao.setDescricao("Promoção de Verão");
        promocao.setDataInicio(LocalDate.now());
        promocao.setDataFim(LocalDate.now().plusDays(10));
        promocao.setDesconto(0.20);  // 20% de desconto
        promocao.setMedicamento(medicamento);

        // Simulando o comportamento do repositório
        when(medicamentoRepository.findById(any(Long.class))).thenReturn(Optional.of(medicamento));
        when(promocaoRepository.save(any(Promocao.class))).thenReturn(promocao);

        promocaoService.save(promocao);

        verify(promocaoRepository, times(1)).save(promocao);
    }

    @Test
    public void testarAplicarPromocao() {
        when(promocaoRepository.findById(any(Long.class))).thenReturn(Optional.of(promocao));
        when(medicamentoRepository.save(any(Medicamento.class))).thenReturn(medicamento);

        promocaoService.aplicarPromocao(1L);

        verify(medicamentoRepository, times(1)).save(any(Medicamento.class));
        assertEquals(8.00, medicamento.getPreco(), 0.01); // Preço esperado após desconto
    }

    @Test
    void testRemoverPromocao() {
        // Criando uma promoção e medicamento
        promocao = new Promocao();
        promocao.setId(1L);
        promocao.setDescricao("Promoção de Verão");
        promocao.setDesconto(0.20); // 20% de desconto
    
        medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setPreco(8.00); // Preço com desconto aplicado
        medicamento.setNome("Paracetamol");
    
        promocao.setMedicamento(medicamento);
    
        // Simulando o comportamento do repositório
        //thenReturn(Optional.of(promocao)) é usado para simular o retorno de um objeto promocao quando o método findById é chamado com o argumento 1L
        when(promocaoRepository.findById(1L)).thenReturn(Optional.of(promocao));
        
        //save(any(Medicamento.class))).thenReturn(medicamento): é usado para simular o retorno de um objeto medicamento quando o método save é chamado
        //com qualquer objeto do tipo Medicamento
        when(medicamentoRepository.save(any(Medicamento.class))).thenReturn(medicamento);
        doNothing().when(promocaoRepository).deleteById(1L);
    
        promocaoService.removerPromocao(1L);
    
        // Verificando se o preço foi restaurado corretamente
        verify(medicamentoRepository, times(1)).save(medicamento);
        verify(promocaoRepository, times(1)).deleteById(1L);
    
        // Ajuste para refletir o valor correto após a remoção da promoção
        assertEquals(10.00, medicamento.getPreco(), 0.01); // Preço original esperado após remover o desconto
    }


    // Teste para o método calcularPrecoOriginal
    @Test 
    public void testCalcularPrecoOriginal() {
        double precoAtual = 8.00;
        double desconto = 0.20;
        double precoEsperado = 10.00;

        double precoOriginal = promocaoService.calcularPrecoOriginal(precoAtual, desconto);

        assertEquals(precoEsperado, precoOriginal, 0.01);
    }


    

    

}
