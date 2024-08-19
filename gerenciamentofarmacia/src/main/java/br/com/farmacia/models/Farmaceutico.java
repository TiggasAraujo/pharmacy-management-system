package br.com.farmacia.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Farmaceutico extends Funcionario {

    private String crf; // Cadastro de Farmacêutico

}
