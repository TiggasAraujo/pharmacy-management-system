package br.com.farmacia.service;

import br.com.farmacia.models.User;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Determine a authority baseada na classe Funcionario
        String role = user.getFuncionario().getClass().getSimpleName().toUpperCase();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(Collections.singleton(authority))
                .build();
    }
}

