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
}
