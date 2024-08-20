package br.com.farmacia.controller;

import br.com.farmacia.models.Cliente;
import br.com.farmacia.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/clientes")
    public ModelAndView listarClientes() {
        ModelAndView modelAndView = new ModelAndView("Cliente/listaClientes");
        List<Cliente> clientes = clienteService.listarClientes();
        modelAndView.addObject("clientes", clientes);
        return modelAndView;
    }

    @GetMapping("/registrarCliente")
    public ModelAndView registrarCliente() {
        ModelAndView modelAndView = new ModelAndView("Cliente/formCliente");
        modelAndView.addObject("cliente", new Cliente());
        return modelAndView;
    }

    @PostMapping("/salvarCliente")
    public ModelAndView salvarCliente(@ModelAttribute("cliente") Cliente cliente, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("Cliente/formCliente");
        }
        clienteService.criarCliente(cliente);
        return new ModelAndView("redirect:/clientes");
    }

    @GetMapping("/editarCliente")
    public ModelAndView editarCliente(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView("Cliente/editarCliente");
        Cliente cliente = clienteService.listarClientes()
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        modelAndView.addObject("cliente", cliente);
        return modelAndView;
    }

    @PostMapping("/atualizarCliente")
    public ModelAndView atualizarCliente(@RequestParam Long id, @ModelAttribute("cliente") Cliente cliente, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("Cliente/editarCliente");
        }
        clienteService.atualizarCliente(id, cliente);
        return new ModelAndView("redirect:/clientes");
    }

    @GetMapping("/deletarCliente")
    public ModelAndView deletarCliente(@RequestParam Long id) {
        clienteService.removerCliente(id);
        return new ModelAndView("redirect:/clientes");
    }
}
