package br.com.farmacia.controller;

import br.com.farmacia.models.User;
import br.com.farmacia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/")
    public String showIndex(Model model, Principal principal) {
        String email = principal.getName();
        
        Optional<User> loggedInUser = userService.findByEmail(email);
        if (loggedInUser.isPresent()) {
            model.addAttribute("user", loggedInUser.get());
        } else {
            // Trate o caso em que o usuário não é encontrado
            model.addAttribute("error", "User not found");
        }
        return "index";
    }
}