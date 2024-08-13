package br.com.farmacia.models;

import jakarta.persistence.Entity;
//import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor -> Se adicionar algum atributo no futuro, retirar o comentário
@Entity
public class Gerente extends Funcionario {

}
