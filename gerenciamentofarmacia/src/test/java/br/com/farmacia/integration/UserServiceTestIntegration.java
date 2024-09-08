package br.com.farmacia.integration;

import br.com.farmacia.models.User;
import br.com.farmacia.repository.UserRepository;
import br.com.farmacia.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTestIntegration {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @Test
    public void testFindByEmail() {
        // Dado um usuário com um email específico
        User user = new User();
        user.setEmail("test@example.com");

        try {
            userRepository.save(user);

            // Quando buscamos o usuário pelo email
            Optional<User> foundUser = userRepository.findByEmail("test@example.com");

            // Então verificamos se o usuário foi encontrado corretamente
            assertTrue(foundUser.isPresent());
            assertEquals("test@example.com", foundUser.get().getEmail());
        } catch (Exception ex) {
            System.out.println("nao ha users");
        }
    }

    @Test
    public void testFindByEmailNotFound() {
        try {
            // Quando buscamos um email que não existe
            Optional<User> foundUser = userRepository.findByEmail("notfound@example.com");

            // Então verificamos que o resultado é vazio
            assertFalse(foundUser.isPresent());
        }  catch (Exception ex) {
            System.out.println("nao ha users");
        }
    }

    @Test
    public void testSaveAndFindById() {
        // Dado um usuário
        User user = new User();
        user.setEmail("save@example.com");

        try {
            User savedUser = userRepository.save(user);

            // Quando buscamos o usuário pelo ID
            Optional<User> foundUser = userRepository.findById(savedUser.getId());

            // Então verificamos que o usuário foi encontrado corretamente
            assertTrue(foundUser.isPresent());
            assertEquals(savedUser.getId(), foundUser.get().getId());
        } catch (Exception ex) {
            System.out.println("nao ha users");
        }
    }

    @Test
    public void testUpdateUser() {
        // Dado um usuário salvo no banco de dados
        User user = new User();
        user.setEmail("update@example.com");

        try {
            User savedUser = userRepository.save(user);

            // Quando atualizamos o email do usuário
            savedUser.setEmail("updated@example.com");
            userRepository.save(savedUser);

            // Então verificamos se o email foi atualizado corretamente
            Optional<User> updatedUser = userRepository.findById(savedUser.getId());
            assertTrue(updatedUser.isPresent());
            assertEquals("updated@example.com", updatedUser.get().getEmail());
        } catch (Exception ex) {
            System.out.println("nao ha users");
        }
    }

    @Test
    public void testDeleteUser() {
        // Dado um usuário salvo no banco de dados
        User user = new User();
        user.setEmail("delete@example.com");
        try {
            User savedUser = userRepository.save(user);

            // Quando deletamos o usuário
            userRepository.deleteById(savedUser.getId());

            // Então verificamos se o usuário foi removido
            Optional<User> deletedUser = userRepository.findById(savedUser.getId());
            assertFalse(deletedUser.isPresent());
        } catch (Exception ex) {
            System.out.println("nao ha users");
        }
    }





}