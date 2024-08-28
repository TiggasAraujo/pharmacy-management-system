package br.com.farmacia.controller;

import br.com.farmacia.models.Venda;
import br.com.farmacia.service.ClienteService;
import br.com.farmacia.service.MedicamentoService;
import br.com.farmacia.service.VendaService;
import br.com.farmacia.service.VendedorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

     @Autowired
    private ClienteService clienteService;

    @Autowired
    private VendedorService vendedorService;

    @Autowired
    private MedicamentoService medicamentoService;

    @GetMapping("/nova")
    public String mostrarFormularioVenda(Model model) {
        model.addAttribute("venda", new Venda());
        model.addAttribute("clientes", clienteService.buscarTodosClientes());
        model.addAttribute("vendedores", vendedorService.buscarTodosVendedores());
        model.addAttribute("medicamentos", medicamentoService.buscarTodosMedicamentos());
        return "Vendas/formVendas";
    }

    @PostMapping("/salvar")
    public String salvarVenda(@Valid Venda venda, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Vendas/formVendas";
        }
        vendaService.salvarVenda(venda);
        return "redirect:/vendas/listar";
    }

    @GetMapping("/listar")
    public String listarVendas(Model model) {
        List<Venda> vendas = vendaService.buscarTodasVendas();
        model.addAttribute("vendas", vendas);
        return "Vendas/listaVendas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable("id") Long id, Model model) {
        Venda venda = vendaService.buscarPorId(id);
        model.addAttribute("venda", venda);
        return "Vendas/editVendas";
    }

    @PostMapping("/editar/{id}")
    public String editarVenda(@PathVariable("id") Long id, @Valid Venda venda, BindingResult result, Model model) {
        if (result.hasErrors()) {
            venda.setId(id);
            return "Vendas/editVendas";
        }
        vendaService.salvarVenda(venda);
        return "redirect:/vendas/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluirVenda(@PathVariable("id") Long id) {
        vendaService.excluirVenda(id);
        return "redirect:/vendas/listar";
    }

    
}
