package br.com.farmacia.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import br.com.farmacia.models.Promocao;
import br.com.farmacia.service.MedicamentoService;
import br.com.farmacia.service.PromocaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import java.util.Collections;

public class PromocaoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PromocaoService promocaoService;

    @Mock
    private MedicamentoService medicamentoService;

    @InjectMocks
    private PromocaoController promocaoController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(promocaoController).build();
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testListarPromocoes() throws Exception {
        when(promocaoService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/promocoes"))
                .andExpect(status().isOk())
                .andExpect(view().name("Promocao/listaPromocoes"))
                .andExpect(model().attributeExists("promocoes"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testCriarPromocaoForm() throws Exception {
        when(medicamentoService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/criarPromocao"))
                .andExpect(status().isOk())
                .andExpect(view().name("Promocao/formPromocao"))
                .andExpect(model().attributeExists("promocao"))
                .andExpect(model().attributeExists("medicamentos"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testEditarPromocaoForm() throws Exception {
        Promocao promocao = new Promocao();
        when(promocaoService.getById(1L)).thenReturn(promocao);
        when(medicamentoService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/editarPromocao/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("Promocao/formPromocao"))
                .andExpect(model().attributeExists("promocao"))
                .andExpect(model().attributeExists("medicamentos"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testAplicarPromocao() throws Exception {
        mockMvc.perform(post("/aplicarPromocao").param("promocaoId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/promocoes"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testRemoverPromocao() throws Exception {
        mockMvc.perform(post("/removerPromocao").param("promocaoId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/promocoes"));
    }
}
