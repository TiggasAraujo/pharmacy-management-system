package br.com.farmacia.controller;

import br.com.farmacia.models.Medicamento;
import br.com.farmacia.models.Promocao;
import br.com.farmacia.service.MedicamentoService;
import br.com.farmacia.service.PromocaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import java.util.List;

@Controller
public class PromocaoController {

    @Autowired
    private PromocaoService promocaoService;

    @Autowired
    private MedicamentoService medicamentoService;

    // Listar todas as promoções
    @GetMapping("/promocoes")
    public ModelAndView listarPromocoes() {
        ModelAndView modelAndView = new ModelAndView("Promocao/listaPromocoes");
        List<Promocao> promocoes = promocaoService.findAll();
        modelAndView.addObject("promocoes", promocoes);
        return modelAndView;
    }

    // Exibir formulário de criação de promoção
    @GetMapping("/criarPromocao")
    public ModelAndView criarPromocaoForm() {
        ModelAndView modelAndView = new ModelAndView("Promocao/formPromocao");
        modelAndView.addObject("promocao", new Promocao());

        List<Medicamento> medicamentos = medicamentoService.findAll();
        modelAndView.addObject("medicamentos", medicamentos);
        return modelAndView;
    }

        // // Criar uma nova promoção
        @PostMapping("/criarPromocao")
        public ModelAndView criarPromocao(@Valid Promocao promocao, @RequestParam("medicamento.id") Long medicamentoId, BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                ModelAndView modelAndView = new ModelAndView("Promocao/formPromocao");

                List<Medicamento> medicamentos = medicamentoService.findAll();
                modelAndView.addObject("medicamentos", medicamentos);
                return modelAndView;
            }

            Medicamento medicamento = medicamentoService.getById(medicamentoId);
            promocao.setMedicamento(medicamento);
            promocaoService.save(promocao);
            return new ModelAndView("redirect:/promocoes");
        }

    // Editar uma promoção existente
    @GetMapping("/editarPromocao/{id}")
    public ModelAndView editarPromocaoForm(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("Promocao/formPromocao");
        Promocao promocao = promocaoService.getById(id);
        modelAndView.addObject("promocao", promocao);

        List<Medicamento> medicamentos = medicamentoService.findAll();
        modelAndView.addObject("medicamentos", medicamentos);
        return modelAndView;
    }

    @PostMapping("/editarPromocao")
    public ModelAndView editarPromocao(@Valid Promocao promocao, @RequestParam("medicamento.id") Long medicamentoId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("Promocao/formPromocao");

            List<Medicamento> medicamentos = medicamentoService.findAll();
            modelAndView.addObject("medicamentos", medicamentos);
            return modelAndView;
        }

        Medicamento medicamento = medicamentoService.getById(medicamentoId);
        promocao.setMedicamento(medicamento);
        promocaoService.save(promocao);
        return new ModelAndView("redirect:/promocoes");
    }

    // Aplicar promoção (lógica de desconto no medicamento)
    @PostMapping("/aplicarPromocao")
    public ModelAndView aplicarPromocao(@RequestParam("promocaoId") Long promocaoId) {
        promocaoService.aplicarPromocao(promocaoId);
        return new ModelAndView("redirect:/promocoes");
    }

    // Remover promoção (reverte o desconto no medicamento)
    @PostMapping("/removerPromocao")
    public ModelAndView removerPromocao(@RequestParam("promocaoId") Long promocaoId) {
        promocaoService.removerPromocao(promocaoId);
        return new ModelAndView("redirect:/promocoes");
    }

    // Excluir uma promoção
    @PostMapping("/excluirPromocao")
    public ModelAndView excluirPromocao(@RequestParam("promocaoId") Long promocaoId) {
        promocaoService.deleteById(promocaoId);
        return new ModelAndView("redirect:/promocoes");
    }
}
