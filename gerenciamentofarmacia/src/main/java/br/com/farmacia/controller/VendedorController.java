package br.com.farmacia.controller;

import br.com.farmacia.models.Vendedor;
import br.com.farmacia.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/vendedores")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    @GetMapping
    public String listarVendedores(Model model) {
        List<Vendedor> vendedores = vendedorService.listarTodos();
        model.addAttribute("vendedores", vendedores);
        return "vendedor/listaVendedores";
    }

    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("vendedor", new Vendedor());
        return "vendedor/formVendedor";
    }

    @PostMapping
    public String cadastrarVendedor(@Valid Vendedor vendedor, BindingResult result) {
        if (result.hasErrors()) {
            return "vendedor/formVendedor";
        }
        vendedorService.salvar(vendedor);
        return "redirect:/vendedores";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable("id") Long id, Model model) {
        Vendedor vendedor = vendedorService.buscarPorId(id);
        model.addAttribute("vendedor", vendedor);
        return "vendedor/editVendedor";
    }

    @PostMapping("/editar/{id}")
    public String editarVendedor(@PathVariable("id") Long id, @Valid Vendedor vendedor, BindingResult result) {
        if (result.hasErrors()) {
            return "vendedor/editVendedor";
        }
        vendedor.setId(id);
        vendedorService.salvar(vendedor);
        return "redirect:/vendedores";
    }

    @GetMapping("/excluir/{id}")
    public String excluirVendedor(@PathVariable("id") Long id) {
        vendedorService.excluir(id);
        return "redirect:/vendedores";
    }
}
