package br.com.farmacia.controller;

import br.com.farmacia.models.Cliente;
import br.com.farmacia.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("José Wellington");
        cliente.setEmail("jose@example.com");
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void deveCriarNovoCliente() throws Exception {
        Mockito.when(clienteService.criarCliente(Mockito.any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(MockMvcRequestBuilders.post("/salvarCliente")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(cliente)))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void deveAtualizarCliente() throws Exception {
        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setId(1L);
        clienteAtualizado.setNome("José Wellington Atualizado");
        clienteAtualizado.setEmail("jose.atualizado@example.com");

        Mockito.when(clienteService.atualizarCliente(Mockito.anyLong(), Mockito.any(Cliente.class))).thenReturn(clienteAtualizado);

        mockMvc.perform(MockMvcRequestBuilders.post("/atualizarCliente")
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .param("id", "1")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .flashAttr("cliente", clienteAtualizado)) // Enviando os dados como form
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection()) // Esperando redirecionamento
            .andExpect(MockMvcResultMatchers.redirectedUrl("/clientes")); // Verificando se redirecionou para a página de listagem
}

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void deveRemoverCliente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/deletarCliente")
                .param("id", "1")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        Mockito.verify(clienteService).removerCliente(1L);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void deveListarTodosClientes() throws Exception {
        // Cria uma lista de clientes para o teste
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        cliente1.setNome("José Wellington");
        cliente1.setEmail("jose@example.com");

        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        cliente2.setNome("Maria Silva");
        cliente2.setEmail("maria@example.com");

        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        // Configura o mock do serviço para retornar a lista de clientes
        Mockito.when(clienteService.listarClientes()).thenReturn(clientes);

        // Realiza a requisição GET para listar todos os clientes
        mockMvc.perform(MockMvcRequestBuilders.get("/clientes"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().attributeExists("clientes"))
            .andExpect(MockMvcResultMatchers.model().attribute("clientes", clientes))
            .andExpect(MockMvcResultMatchers.view().name("Cliente/listaClientes")); 
    }


}
