// Source code is decompiled from a .class file using FernFlower decompiler.
package br.com.farmacia.controller;

import br.com.farmacia.models.Estoque;
import br.com.farmacia.models.Medicamento;
import br.com.farmacia.service.EstoqueService;
import br.com.farmacia.service.MedicamentoService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest({EstoqueController.class})
public class EstoqueControllerTest {
   @Autowired
   private MockMvc mockMvc;
   @MockBean
   private EstoqueService estoqueService;
   @MockBean
   private MedicamentoService medicamentoService;

   public EstoqueControllerTest() {
   }

   @Test
   @WithMockUser(
      username = "user",
      roles = {"USER"}
   )
   public void RegisterEstoque() throws Exception {
      Medicamento medicamento1 = new Medicamento();
      medicamento1.setNome("Paracetamol");
      medicamento1.setPreco(15.99);
      medicamento1.setDescricao("Analg\u00e9sico e antit\u00e9rmico");
      medicamento1.setPrecisaReceita(false);
      Medicamento medicamento2 = new Medicamento();
      medicamento2.setNome("Amoxicilina");
      medicamento2.setPreco(25.5);
      medicamento2.setDescricao("Antibi\u00f3tico para infec\u00e7\u00f5es bacterianas");
      medicamento2.setPrecisaReceita(true);
      Mockito.when(this.medicamentoService.findAll()).thenReturn(Arrays.asList(medicamento1, medicamento2));
      this.mockMvc.perform(MockMvcRequestBuilders.get("/registrarEstoque", new Object[0])).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("Estoque/formEstoque")).andExpect(MockMvcResultMatchers.model().attributeExists(new String[]{"estoque"})).andExpect(MockMvcResultMatchers.model().attribute("medicamentos", Arrays.asList(medicamento1, medicamento2)));
   }

   @Test
   @WithMockUser(
      username = "user",
      roles = {"USER"}
   )
   public void registrarCliente() throws Exception {
      Medicamento mockMedicamento = new Medicamento();
      mockMedicamento.setId(1L);
      mockMedicamento.setNome("Medicamento Teste");
      mockMedicamento.setPreco(10.0);
      mockMedicamento.setDescricao("Descri\u00e7\u00e3o do Medicamento Teste");
      mockMedicamento.setPrecisaReceita(false);
      Mockito.when(this.medicamentoService.getById(1L)).thenReturn(mockMedicamento);
      this.mockMvc.perform(MockMvcRequestBuilders.post("/registerEstoque", new Object[0]).param("quantidade", new String[]{"10"}).param("medicamento.id", new String[]{"1"}).with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/estoques-adicionados"));
   }

   @Test
   @WithMockUser(
      username = "user",
      roles = {"USER"}
   )
   public void editarGET() throws Exception {
      Estoque Estoque = new Estoque();
      Estoque.setId(1L);
      Estoque.setQtdEstoque(20);
      Medicamento Medicamento1 = new Medicamento();
      Medicamento1.setId(1L);
      Medicamento1.setNome("Medicamento 1");
      Medicamento1.setPreco(10.0);
      Medicamento1.setDescricao("Descri\u00e7\u00e3o 1");
      Medicamento1.setPrecisaReceita(false);
      Medicamento Medicamento2 = new Medicamento();
      Medicamento2.setId(2L);
      Medicamento2.setNome("Medicamento 2");
      Medicamento2.setPreco(20.0);
      Medicamento2.setDescricao("Descri\u00e7\u00e3o 2");
      Medicamento2.setPrecisaReceita(true);
      List<Medicamento> medicamentos = Arrays.asList(Medicamento1, Medicamento2);
      Mockito.when(this.estoqueService.getById(1L)).thenReturn(Estoque);
      Mockito.when(this.medicamentoService.findAll()).thenReturn(medicamentos);

      this.mockMvc.perform(MockMvcRequestBuilders.get("/editar/1", new Object[0]))
      .andExpect(MockMvcResultMatchers.status()
      .isOk())
      .andExpect(MockMvcResultMatchers.view()
      .name("Estoque/formEstoque"))
      .andExpect(MockMvcResultMatchers.model()
      .attributeExists(new String[]{"estoque"}))
      .andExpect(MockMvcResultMatchers.model()
      .attributeExists(new String[]{"medicamentos"}))
      .andExpect(MockMvcResultMatchers.model()
      .attribute("estoque", Estoque))
      .andExpect(MockMvcResultMatchers.model()
      .attribute("medicamentos", medicamentos));
   }

//    @Test
//    @WithMockUser(username = "user", roles = {"USER"})
//    public void editarPOST() throws Exception {
//       Medicamento medicamento = new Medicamento();
//       medicamento.setId(1L);
//       medicamento.setNome("Paracetamol");
//       medicamento.setPreco(15.0);
//       medicamento.setDescricao("Uso para dor e febre");
//       medicamento.setPrecisaReceita(false);
//       medicamento.setValidade(LocalDate.of(2025, 12, 31));
//       Estoque estoqueExistente = new Estoque();
//       estoqueExistente.setId(1L);
//       estoqueExistente.setQtdEstoque(50);
//       estoqueExistente.setMedicamento(medicamento);
//       Mockito.when(this.estoqueService.getById(1L)).thenReturn(estoqueExistente);
//       Mockito.when(this.medicamentoService.findAll()).thenReturn(Arrays.asList(medicamento));

//       this.mockMvc.perform(MockMvcRequestBuilders.post("/editar", 
//       new Object[0]).param("id", new String[]{"1"})
//       .param("quantidade", new String[]{"100"}).param("medicamentoid", new String[]{"1"})
//       .with(SecurityMockMvcRequestPostProcessors.csrf()))
//       .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//       .andExpect(MockMvcResultMatchers.view().name("redirect:/estoques-adicionados"));
//    }

   @Test
   @WithMockUser(
      username = "user",
      roles = {"USER"}
   )
   public void removerEstoque() throws Exception {
      Long id = 1L;
      this.mockMvc.perform(MockMvcRequestBuilders.get("/remover/{id}", new Object[]{id})).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/estoques-adicionados"));
      ((EstoqueService)Mockito.verify(this.estoqueService)).deleteByid(id);
   }

   @Test
   @WithMockUser(
      username = "user",
      roles = {"USER"}
   )
   public void listaEstoques() throws Exception {
      Medicamento Medicamento1 = new Medicamento();
      Medicamento1.setId(1L);
      Medicamento1.setNome("Medicamento 1");
      Medicamento1.setPreco(10.0);
      Medicamento1.setDescricao("Descri\u00e7\u00e3o 1");
      Medicamento1.setPrecisaReceita(false);
      Medicamento Medicamento2 = new Medicamento();
      Medicamento2.setId(1L);
      Medicamento2.setNome("Medicamento 2");
      Medicamento2.setPreco(15.0);
      Medicamento2.setDescricao("Descri\u00e7\u00e3o 2");
      Medicamento2.setPrecisaReceita(false);
      Estoque estoque1 = new Estoque();
      estoque1.setId(1L);
      estoque1.setQtdEstoque(20);
      estoque1.setMedicamento(Medicamento1);
      Estoque estoque2 = new Estoque();
      estoque2.setId(2L);
      estoque2.setQtdEstoque(30);
      estoque2.setMedicamento(Medicamento2);
      List<Estoque> estoques = Arrays.asList(estoque1, estoque2);
      Mockito.when(this.estoqueService.findAll()).thenReturn(estoques);
      this.mockMvc.perform(MockMvcRequestBuilders.get("/estoques-adicionados", new Object[0])).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("Estoque/listaEstoques")).andExpect(MockMvcResultMatchers.model().attributeExists(new String[]{"estoques"})).andExpect(MockMvcResultMatchers.model().attribute("estoques", estoques));
   }
}
