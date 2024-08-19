package br.com.farmacia.controller;


import br.com.farmacia.models.Gerente;
import br.com.farmacia.models.Promocao;
import br.com.farmacia.service.GerenteService;
import br.com.farmacia.service.PromocaoService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GerenteController {

    @Autowired
    private GerenteService gerenteService;

    @Autowired
    private PromocaoService promocaoService;

    @GetMapping("/enviarPromocao")
    public ModelAndView mostrarFormularioEnvioPromocao() {
        ModelAndView modelAndView = new ModelAndView("Gerente/enviarPromocao");

        // Recuperar listas de gerentes e promoções
        List<Gerente> gerentes = gerenteService.findAll();
        List<Promocao> promocoes = promocaoService.findAll();

        modelAndView.addObject("gerentes", gerentes);
        modelAndView.addObject("promocoes", promocoes);

        return modelAndView;
    }

    @PostMapping("/enviarPromocao")
    public ModelAndView enviarPromocao(@RequestParam("gerente.id") Long gerenteId, @RequestParam("promocao.id") Long promocaoId) {
        gerenteService.enviarPromocaoParaClientes(gerenteId, promocaoId);
        ModelAndView modelAndView = new ModelAndView("redirect:/promocoes");
        modelAndView.addObject("mensagem", "Promoção enviada com sucesso para todos os clientes!");
        return modelAndView;
    }

    @GetMapping("/gerentes")
    public ModelAndView listarGerentes() {
        ModelAndView modelAndView = new ModelAndView("Gerente/listaGerentes");
        modelAndView.addObject("gerentes", gerenteService.findAll());
        return modelAndView;
    }

    @GetMapping("/registrarGerente")
    public ModelAndView registrarGerente() {
        ModelAndView modelAndView = new ModelAndView("Gerente/formGerente");
        modelAndView.addObject("gerente", new Gerente());
        return modelAndView;
    }

    @PostMapping("/salvarGerente")
    public ModelAndView salvarGerente(@ModelAttribute("gerente") Gerente gerente, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("Gerente/formGerente");
        }
        gerenteService.save(gerente);
        return new ModelAndView("redirect:/gerentes");
    }
}
