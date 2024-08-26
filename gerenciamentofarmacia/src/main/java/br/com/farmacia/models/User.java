package br.com.farmacia.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")  // Altere o nome da tabela para 'users'
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // email unico
    @Column(unique = true)
    private String email;
    private String password;

    @OneToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    
}
