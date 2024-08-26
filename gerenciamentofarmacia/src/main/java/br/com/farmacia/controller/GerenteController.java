package br.com.farmacia.controller;

import br.com.farmacia.models.Farmaceutico;
import br.com.farmacia.models.Funcionario;
import br.com.farmacia.models.Gerente;
import br.com.farmacia.models.Promocao;
import br.com.farmacia.models.User;
import br.com.farmacia.models.Vendedor;
import br.com.farmacia.service.FuncionarioService;
import br.com.farmacia.service.GerenteService;
import br.com.farmacia.service.PromocaoService;
import br.com.farmacia.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GerenteController {

    @Autowired
    private GerenteService gerenteService;

    @Autowired
    private PromocaoService promocaoService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private UserService userService;

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
    public ModelAndView enviarPromocao(/* @RequestParam("gerente.id") Long gerenteId, */
            @RequestParam("promocao.id") Long promocaoId) {
        gerenteService.enviarPromocaoParaClientes(/* gerenteId, */ promocaoId);
        ModelAndView modelAndView = new ModelAndView("redirect:/promocoes");
        modelAndView.addObject("mensagem", "Promoção enviada com sucesso para todos os clientes!");
        return modelAndView;
    }

    @GetMapping("/add-employee")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("user", new User());

        // Lista de tipos de funcionários disponíveis (Farmaceutico e Vendedor)
        List<String> roles = new ArrayList<>();
        roles.add("Farmaceutico");
        roles.add("Vendedor");

        // Adiciona a lista de roles ao modelo
        model.addAttribute("roles", roles);

        return "User/add-employee"; // Nome da view (HTML)
    }

    @PostMapping("/add-employee")
    public String addEmployee(@ModelAttribute User user, @RequestParam String role,
            @RequestParam String nome, @RequestParam String cpf,
            @RequestParam BigDecimal salario, @RequestParam LocalDate dataAdmissao) {

        Funcionario funcionario;

        switch (role) {
            case "Farmaceutico":
                funcionario = new Farmaceutico();
                break;
            case "Vendedor":
                funcionario = new Vendedor();
                break;
            default:
                throw new IllegalArgumentException("Tipo de funcionário inválido");
        }

        // Preenche o Funcionario com os dados do formulário
        funcionario.setNome(nome);
        funcionario.setCpf(cpf);
        funcionario.setSalario(salario);
        funcionario.setDataAdmissao(dataAdmissao);

        // Salva o Funcionario no banco de dados
        funcionario = funcionarioService.save(funcionario);

        // Associa o Funcionario ao User
        user.setFuncionario(funcionario);

        // Salva o User no banco de dados
        userService.save(user);

        return "redirect:/gerentes";
    }

    @GetMapping("/gerentes")
    public String listEmployees(Model model) {
        List<User> employees = userService.findAllEmployees();
        model.addAttribute("employees", employees);
        return "User/list-employees";
    }

    @GetMapping("/edit-employee/{id}")
    public ModelAndView editarFuncionarioForm(@PathVariable("id") Long id) {
        // Cria uma nova instância de ModelAndView
        ModelAndView modelAndView = new ModelAndView("User/edit-employee");

        // Encontra o usuário pelo ID
        User user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado: " + id));

        // Adiciona o usuário ao modelo
        modelAndView.addObject("user", user);

        // Adiciona a lista de tipos de funcionários disponíveis ao modelo
        List<String> roles = new ArrayList<>();
        roles.add("Farmaceutico");
        roles.add("Vendedor");

        modelAndView.addObject("roles", roles);

        return modelAndView;
    }

    @PostMapping("/edit-employee")
    public ModelAndView editarFuncionario(@ModelAttribute User user, @RequestParam String role,
            @RequestParam String nome, @RequestParam String cpf,
            @RequestParam BigDecimal salario, @RequestParam LocalDate dataAdmissao) {

        if (user.getId() == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");
        }

        User existingUser = userService.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado: " + user.getId()));

        Funcionario funcionario = existingUser.getFuncionario();

        // Atualiza as propriedades do Funcionario
        if (funcionario == null) {
            switch (role) {
                case "Farmaceutico":
                    funcionario = new Farmaceutico();
                    break;
                case "Vendedor":
                    funcionario = new Vendedor();
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de funcionário inválido");
            }
        }

        funcionario.setNome(nome);
        funcionario.setCpf(cpf);
        funcionario.setSalario(salario);
        funcionario.setDataAdmissao(dataAdmissao);

        // Salva o Funcionario no banco de dados
        funcionario = funcionarioService.save(funcionario);

        // Atualiza a associação do Funcionario no User
        user.setFuncionario(funcionario);
        userService.save(user);

        return new ModelAndView("redirect:/gerentes");
    }

    @GetMapping("/delete-employee")
    public String deleteEmployee(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/gerentes"; // Redireciona para a lista de funcionários
    }

}
