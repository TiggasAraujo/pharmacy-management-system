package br.com.farmacia.models;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Gerente extends Funcionario {
    

}
