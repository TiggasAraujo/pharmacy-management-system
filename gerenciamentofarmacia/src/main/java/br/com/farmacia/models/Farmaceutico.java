package br.com.farmacia.models;

import jakarta.persistence.Entity;

//import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
//@AllArgsConstructor -> Se adicionar algum atributo no futuro, retirar o comentário
public class Farmaceutico extends Funcionario {

}

