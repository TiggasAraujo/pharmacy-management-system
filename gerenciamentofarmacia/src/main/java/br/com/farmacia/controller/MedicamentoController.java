package br.com.farmacia.controller;

import br.com.farmacia.models.Medicamento;
import br.com.farmacia.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @GetMapping("/cadastrar")
    public ModelAndView cadastrarMedicamento() {
        ModelAndView modelAndView = new ModelAndView("Medicamentos/formMedicamentos");
        modelAndView.addObject("medicamento", new Medicamento());
        return modelAndView;
    }

    @PostMapping("/cadastrar")
    public ModelAndView salvarMedicamento(@Valid Medicamento medicamento, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("Medicamentos/formMedicamentos");
        } else {
            medicamentoService.save(medicamento);
            modelAndView.setViewName("redirect:/medicamentos");
        }
        return modelAndView;
    }


    @GetMapping("")
    public ModelAndView listarMedicamentos() {
    ModelAndView modelAndView = new ModelAndView("Medicamentos/listaMedicamentos");
    List<Medicamento> medicamentos = medicamentoService.findAll();
    modelAndView.addObject("medicamentos", medicamentos);
    return modelAndView;
    }


    @GetMapping("/editar/{id}")
public ModelAndView editarMedicamento(@PathVariable("id") Long id) {
    ModelAndView modelAndView = new ModelAndView("Medicamentos/editMedicamentos");
    Medicamento medicamento = medicamentoService.getById(id);

    // Passa o medicamento existente para o formulário de edição
    modelAndView.addObject("medicamento", medicamento);
    return modelAndView;
}

@PostMapping("/atualizar")
public ModelAndView atualizarMedicamento(@Valid Medicamento medicamento, BindingResult bindingResult) {
    ModelAndView modelAndView = new ModelAndView();

    if (bindingResult.hasErrors()) {
        modelAndView.setViewName("Medicamentos/editMedicamentos");
        return modelAndView;
    }

    try {
        medicamentoService.save(medicamento); 
        modelAndView.setViewName("redirect:/medicamentos");
    } catch (Exception e) {
        modelAndView.setViewName("error");
        modelAndView.addObject("message", "Erro ao atualizar o medicamento.");
    }

    return modelAndView;
}


    @GetMapping("/remover/{id}")
    public String removerMedicamento(@PathVariable("id") Long id) {
        medicamentoService.deleteById(id);
        return "redirect:/medicamentos";
    }
}
