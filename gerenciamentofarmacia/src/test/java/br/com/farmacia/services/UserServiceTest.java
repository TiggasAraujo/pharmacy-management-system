package br.com.farmacia.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.farmacia.models.User;
import br.com.farmacia.repository.UserRepository;
import br.com.farmacia.service.UserService;



public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;


    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testFindByEmail() {

        String email = "test@example.com";
        User userTeste = new User();
        userTeste.setEmail(email);

        // Configura o comportamento do mock
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userTeste));

        // Chama o método a ser testado
        Optional<User> result = userService.findByEmail(email);

        // Verifica o resultado
        assertEquals(Optional.of(userTeste), result);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testFindById() {

        Long userId = 1L;
        User userTeste2 = new User();
        userTeste2.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userTeste2));

        Optional<User> result = userService.findById(userId);

        assertEquals(Optional.of(userTeste2), result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testSave(){

        User user = new User();
        user.setPassword("plainPassword");  // Senha original
        User savedUser = new User();
        savedUser.setPassword("encodedPassword"); // Senha codificada esperada

        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.save(user);

        assertEquals(savedUser, result);
        verify(passwordEncoder, times(1)).encode("plainPassword"); // Verifica se o encode foi chamado com a senha original
        verify(userRepository, times(1)).save(user); // Verifica se o save foi chamado com o usuário
    }

    @Test
    public void testDeleteById() {

        Long userId = 1L;

        userService.deleteById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testFindById_UserNotFound() {
        Long userId = 1L;

        // Configura o comportamento do mock para retornar um Optional vazio
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Chama o método a ser testado
        Optional<User> result = userService.findById(userId);

        // Verifica se o resultado é um Optional.empty()
        assertEquals(Optional.empty(), result);
        verify(userRepository, times(1)).findById(userId);
    }

}



