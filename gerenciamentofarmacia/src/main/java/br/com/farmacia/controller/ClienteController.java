package br.com.farmacia.controller;

import br.com.farmacia.models.Cliente;
import br.com.farmacia.models.Venda;
import br.com.farmacia.service.ClienteService;
import br.com.farmacia.service.VendaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VendaService vendaService;

    @GetMapping("/clientes")
    public ModelAndView listarClientes() {
        ModelAndView modelAndView = new ModelAndView("Cliente/listaClientes");
        List<Cliente> clientes = clienteService.buscarTodosClientes();
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
        Cliente cliente = clienteService.buscarTodosClientes()
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

    // Adicionado: Busca de cliente por ID
    @GetMapping("/Cliente/{id}")
    public ModelAndView buscarClientePorId(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView("Cliente/buscaCliente");
        Cliente cliente = clienteService.buscarTodosClientes()
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        modelAndView.addObject("cliente", cliente);
        return modelAndView;
    }

    @GetMapping("/clientes/{clienteId}/compras")
public ModelAndView getHistoricoCompras(@PathVariable("clienteId") Long clienteId) {
    ModelAndView modelAndView = new ModelAndView("Cliente/comprasCliente");

    // Busca o cliente pelo ID
    Cliente cliente = clienteService.buscarPorId(clienteId);

    // Busca o histórico de compras do cliente
    List<Venda> vendas = vendaService.buscarVendasPorCliente(cliente);

    // Adiciona o cliente e as vendas ao modelo
    modelAndView.addObject("cliente", cliente);
    modelAndView.addObject("vendas", vendas);

    // Retorna a view que exibirá o histórico de compras
    return modelAndView;
}


    

    
}
