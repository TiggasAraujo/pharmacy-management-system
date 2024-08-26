package br.com.farmacia;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.farmacia.models.Gerente;
import br.com.farmacia.models.User;
import br.com.farmacia.repository.UserRepository;
import br.com.farmacia.repository.FuncionarioRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataLoader {

    // Criar um usuário gerente padrão no início de cada execução da aplicação
    @Bean
    public CommandLineRunner loadData(UserRepository userRepository, 
                                      FuncionarioRepository funcionarioRepository, 
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            // Verifica se o usuário já existe
            if (userRepository.findByEmail("engenhariadesoftware403@gmail.com").isEmpty()) {
                // Cria um novo gerente
                Gerente gerente = new Gerente();
                gerente.setNome("Administrador");
                gerente.setEmail("engenhariadesoftware403@gmail.com");
                gerente.setCpf("000.000.000-00");
                gerente.setSalario(new BigDecimal("5000.00"));
                gerente.setDataAdmissao(LocalDate.now());

                // Salva o gerente no banco de dados
                funcionarioRepository.save(gerente);

                // Cria um novo usuário associado ao gerente
                User admin = new User();
                admin.setEmail("engenhariadesoftware403@gmail.com");
                admin.setPassword(passwordEncoder.encode("12345678"));
                admin.setFuncionario(gerente);  // Associa o gerente ao usuário
                
                userRepository.save(admin);
            }
        };
    }
}
