package br.com.farmacia.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import br.com.farmacia.models.Cliente;
import br.com.farmacia.models.ItemVenda;
import br.com.farmacia.models.Medicamento;
import br.com.farmacia.models.Venda;
import br.com.farmacia.models.Vendedor;
import br.com.farmacia.service.ClienteService;
import br.com.farmacia.service.MedicamentoService;
import br.com.farmacia.service.VendaService;
import br.com.farmacia.service.VendedorService;

@SpringBootTest
@AutoConfigureMockMvc
public class VendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ClienteService clienteService;

    @Mock
    private VendedorService vendedorService;

    @Mock
    private MedicamentoService medicamentoService;

    @InjectMocks
    private VendaController vendaController;

    @Mock
    private VendaService vendaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vendaController).build();
    }

    @Test
    public void testMostrarFormularioVenda() throws Exception {
        // Dados mockados para os serviços
        when(clienteService.buscarTodosClientes()).thenReturn(Arrays.asList(new Cliente(), new Cliente()));
        when(vendedorService.buscarTodosVendedores()).thenReturn(Arrays.asList(new Vendedor(), new Vendedor()));
        when(medicamentoService.findAll()).thenReturn(Arrays.asList(new Medicamento(), new Medicamento()));

        mockMvc.perform(get("/vendas/nova"))
            .andExpect(status().isOk())
            .andExpect(view().name("Vendas/formVendas"))
            .andExpect(model().attributeExists("venda"))
            .andExpect(model().attributeExists("clientes"))
            .andExpect(model().attributeExists("vendedores"))
            .andExpect(model().attributeExists("medicamentos"));
    }


    @Test
    public void testSalvarVenda_RedirectsToListar() throws Exception {
        // Dados de exemplo para um cliente, vendedor e medicamento
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");
        cliente.setCpf("123.456.789-00");
        cliente.setEmail("email@");

        Vendedor vendedor = new Vendedor();
        vendedor.setId(1L);
        vendedor.setNome("Vendedor Teste");
        vendedor.setCpf("123.456.789-00");
        vendedor.setEmail("email@");
        vendedor.setSalario(new BigDecimal("1000.00"));
        vendedor.setDataAdmissao(LocalDate.now());

        Medicamento medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setNome("Medicamento Teste");
        medicamento.setPreco(25.00);
        medicamento.setDescricao("Descrição do medicamento");
        medicamento.setPrecisaReceita(false);
        medicamento.setValidade(LocalDate.now());
        medicamento.setEstoque(null);
        medicamento.setReceitas(null);

        Venda venda = new Venda();
        venda.setData(LocalDate.now());
        venda.setCliente(cliente);
        venda.setVendedor(vendedor);
        ItemVenda itemVenda = new ItemVenda(1L, medicamento, 2);
        venda.setItens(Arrays.asList(itemVenda));
        venda.setValorTotal(50.00);

        // Mock dos serviços para retornar os dados de exemplo
        when(vendaService.salvarVenda(any(Venda.class))).thenReturn(venda);

        mockMvc.perform(post("/vendas/salvar")
                .flashAttr("venda", venda))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vendas/listar"));

        // Verifica se o método salvarVenda foi chamado
        verify(vendaService, times(1)).salvarVenda(any(Venda.class));
    }

    @Test
    void testListarVendas() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");
        cliente.setCpf("123.456.789-00");
        cliente.setEmail("email@");

        Vendedor vendedor = new Vendedor();
        vendedor.setId(1L);
        vendedor.setNome("Vendedor Teste");
        vendedor.setCpf("123.456.789-00");
        vendedor.setEmail("email@");
        vendedor.setSalario(new BigDecimal("1000.00"));
        vendedor.setDataAdmissao(LocalDate.now());

        Medicamento medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setNome("Medicamento Teste");
        medicamento.setPreco(25.00);
        medicamento.setDescricao("Descrição do medicamento");
        medicamento.setPrecisaReceita(false);
        medicamento.setValidade(LocalDate.now());
        medicamento.setEstoque(null);
        medicamento.setReceitas(null);

        Venda venda = new Venda();
        venda.setData(LocalDate.now());
        venda.setCliente(cliente);
        venda.setVendedor(vendedor);
        ItemVenda itemVenda = new ItemVenda(1L, medicamento, 2);
        venda.setItens(Arrays.asList(itemVenda));
        venda.setValorTotal(50.00);

        List<Venda> vendas = Arrays.asList(venda);

        // Configure o comportamento do serviço de vendas
        when(vendaService.buscarTodasVendas()).thenReturn(vendas);

        mockMvc.perform(get("/vendas/listar"))
                .andExpect(status().isOk())
                .andExpect(view().name("Vendas/listaVendas"))
                .andExpect(model().attribute("vendas", vendas));
    }


    @Test
    void testMostrarFormularioEdicao() throws Exception {
        Long id = 1L;
        
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");
        cliente.setCpf("123.456.789-00");
        cliente.setEmail("email@");

        Vendedor vendedor = new Vendedor();
        vendedor.setId(1L);
        vendedor.setNome("Vendedor Teste");
        vendedor.setCpf("123.456.789-00");
        vendedor.setEmail("email@");
        vendedor.setSalario(new BigDecimal("1000.00"));
        vendedor.setDataAdmissao(LocalDate.now());

        Medicamento medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setNome("Medicamento Teste");
        medicamento.setPreco(25.00);
        medicamento.setDescricao("Descrição do medicamento");
        medicamento.setPrecisaReceita(false);
        medicamento.setValidade(LocalDate.now());
        medicamento.setEstoque(null);
        medicamento.setReceitas(null);

        Venda venda = new Venda();
        venda.setData(LocalDate.now());
        venda.setCliente(cliente);
        venda.setVendedor(vendedor);
        ItemVenda itemVenda = new ItemVenda(1L, medicamento, 2);
        venda.setItens(Arrays.asList(itemVenda));
        venda.setValorTotal(50.00);

        List<Cliente> clientes = Arrays.asList(cliente);
        List<Vendedor> vendedores = Arrays.asList(vendedor);
        List<Medicamento> medicamentos = Arrays.asList(medicamento);

        // Configura o comportamento dos serviços
        when(vendaService.buscarPorId(id)).thenReturn(venda);
        when(clienteService.buscarTodosClientes()).thenReturn(clientes);
        when(vendedorService.buscarTodosVendedores()).thenReturn(vendedores);
        when(medicamentoService.findAll()).thenReturn(medicamentos);

        mockMvc.perform(get("/vendas/editar/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("Vendas/editVendas"))
                .andExpect(model().attribute("venda", venda))
                .andExpect(model().attribute("clientes", clientes))
                .andExpect(model().attribute("vendedores", vendedores))
                .andExpect(model().attribute("medicamentos", medicamentos));
    }

    @Test
    void testExcluirVenda() throws Exception {
        Long id = 1L;

        // Simula a chamada do serviço de exclusão
        doNothing().when(vendaService).excluirVenda(id);

        mockMvc.perform(get("/vendas/excluir/{id}", id))
                .andExpect(status().is3xxRedirection()) // Verifica se o status é redirecionamento
                .andExpect(redirectedUrl("/vendas/listar")); // Verifica se o redirecionamento é para /vendas/listar

        // Verifica se o método excluirVenda foi chamado uma vez com o ID fornecido
        verify(vendaService, times(1)).excluirVenda(id);
    }

    @Test
    void testEditarVendaPost() throws Exception {
        Long id = 1L;

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");
        cliente.setCpf("123.456.789-00");
        cliente.setEmail("email@");

        Vendedor vendedor = new Vendedor();
        vendedor.setId(1L);
        vendedor.setNome("Vendedor Teste");
        vendedor.setCpf("123.456.789-00");
        vendedor.setEmail("email@");
        vendedor.setSalario(new BigDecimal("1000.00"));
        vendedor.setDataAdmissao(LocalDate.now());

        Medicamento medicamento = new Medicamento();
        medicamento.setId(1L);
        medicamento.setNome("Medicamento Teste");
        medicamento.setPreco(25.00);
        medicamento.setDescricao("Descrição do medicamento");
        medicamento.setPrecisaReceita(false);
        medicamento.setValidade(LocalDate.now());
        medicamento.setEstoque(null);
        medicamento.setReceitas(null);

        Venda venda = new Venda();
        venda.setData(LocalDate.now());
        venda.setCliente(cliente);
        venda.setVendedor(vendedor);
        ItemVenda itemVenda = new ItemVenda(1L, medicamento, 2);
        venda.setItens(Arrays.asList(itemVenda));
        venda.setValorTotal(50.00);

        // Configura o comportamento dos serviços
        when (vendaService.salvarVenda(venda)).thenReturn(venda);
    
        mockMvc.perform(post("/vendas/editar/{id}", id)
                .flashAttr("venda", venda)) // Adiciona o objeto Venda ao flash attributes
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vendas/listar"));

        // Verifica se o método salvarVenda foi chamado uma vez com a venda fornecida
        verify(vendaService, times(1)).salvarVenda(venda);
    }
}
