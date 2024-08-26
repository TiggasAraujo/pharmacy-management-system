// package br.com.farmacia.controller;
// import br.com.farmacia.models.Gerente;
// import br.com.farmacia.models.Promocao;
// import br.com.farmacia.service.GerenteService;
// import br.com.farmacia.service.PromocaoService;

// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.beans.factory.annotation.Autowired;


// import java.util.Arrays;

// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

// @WebMvcTest(GerenteController.class)
// public class GerenteControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private GerenteService gerenteService;

//     @MockBean
//     private PromocaoService promocaoService;

//     @Test
//     @WithMockUser(username = "user", roles = {"USER"})
//     public void testMostrarFormularioEnvioPromocao() throws Exception {
//         Gerente gerente = new Gerente();
//         Promocao promocao = new Promocao();
        
//         when(gerenteService.findAll()).thenReturn(Arrays.asList(gerente));
//         when(promocaoService.findAll()).thenReturn(Arrays.asList(promocao));
        
//         mockMvc.perform(get("/enviarPromocao"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("Gerente/enviarPromocao"))
//                .andExpect(model().attributeExists("gerentes"))
//                .andExpect(model().attributeExists("promocoes"));
//     }
// }   
