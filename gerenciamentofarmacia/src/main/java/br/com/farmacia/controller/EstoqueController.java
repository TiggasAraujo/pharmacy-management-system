package br.com.farmacia.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.farmacia.models.Estoque;
import br.com.farmacia.models.Medicamento;
import br.com.farmacia.service.EstoqueService;
import br.com.farmacia.service.MedicamentoService;
import jakarta.validation.Valid;

@Controller
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private MedicamentoService medicamentoService;

    @GetMapping("/registrarEstoque")
    public ModelAndView registerEstoque() {
        ModelAndView modelAndView = new ModelAndView("Estoque/formEstoque");
        modelAndView.addObject("estoque", new Estoque());

        List<Medicamento> medicamentos = medicamentoService.findAll();
        modelAndView.addObject("medicamentos", medicamentos);
        
        return modelAndView;
    }

    @PostMapping("registerEstoque")
    public ModelAndView registrarEstoque(@Valid Estoque estoque, @RequestParam("medicamento.id") Long medicamentoId, BindingResult bindingResult) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("Estoque/formEstoque");

            // Passando os medicamentos novamente se houver erro de validação
            List<Medicamento> medicamentos = medicamentoService.findAll();
            modelAndView.addObject("medicamentos", medicamentos);
        } else {
            Medicamento medicamento = medicamentoService.getById(medicamentoId);
            estoque.setMedicamento(medicamento);
            estoqueService.save(estoque);
            modelAndView.setViewName("redirect:/estoques-adicionados");
        }
        return modelAndView;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("Estoque/formEstoque");
        Estoque estoque = estoqueService.getById(id);

        // Passa a lista de medicamentos para o dropdown na edição
        List<Medicamento> medicamentos = medicamentoService.findAll();
        modelAndView.addObject("medicamentos", medicamentos);

        modelAndView.addObject("estoque", estoque);
        return modelAndView;
    }

    @PostMapping("/editar")
    public ModelAndView editar(@Valid Estoque estoque, @RequestParam("medicamento.id") Long medicamentoId, BindingResult bindingResult) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("Estoque/formEstoque");

            // Passando os medicamentos novamente se houver erro de validação
            List<Medicamento> medicamentos = medicamentoService.findAll();
            modelAndView.addObject("medicamentos", medicamentos);
        } else {
            Medicamento medicamento = medicamentoService.getById(medicamentoId);
            estoque.setMedicamento(medicamento);
            estoqueService.save(estoque);
            modelAndView.setViewName("redirect:/estoques-adicionados");
        }
        return modelAndView;
    }



    @GetMapping("/remover/{id}")
    public String removerEstoque(@PathVariable("id") Long id) {
        estoqueService.deleteById(id);
        return "redirect:/estoques-adicionados";
    }

    @GetMapping("/estoques-adicionados")
    public ModelAndView listarEstoques() {
        ModelAndView modelAndView = new ModelAndView("Estoque/listaEstoques");
        List<Estoque> estoques = estoqueService.findAll();
        modelAndView.addObject("estoques", estoques);
        return modelAndView;
    }
}
