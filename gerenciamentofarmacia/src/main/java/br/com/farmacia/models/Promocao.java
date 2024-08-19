package br.com.farmacia.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Promocao {

  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    private Double desconto;

    @ManyToOne
    @JoinColumn(name = "medicamento_id")
    private Medicamento medicamento;

    @ManyToOne
    @JoinColumn(name = "gerente_id")
    private Gerente gerente;

    // Método para aplicar o desconto ao preço do medicamento
    public void aplicarDesconto() {
        if (medicamento != null) {
            Double novoPreco = medicamento.getPreco() - (medicamento.getPreco() * (desconto / 100));
            medicamento.setPreco(novoPreco);
        }
    }

    // Método para remover o desconto (reverter ao preço original)
    public void removerDesconto(Double precoOriginal) {
        if (medicamento != null) {
            medicamento.setPreco(precoOriginal);
        }
    }
    
}
