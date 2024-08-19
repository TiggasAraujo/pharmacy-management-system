package br.com.farmacia.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Double preco;

    private String descricao;

    private boolean precisaReceita;

    @OneToOne(cascade = CascadeType.ALL)
    private Estoque estoque;

    private LocalDate validade;

    @ManyToMany(mappedBy = "medicamentos")
    private List<Receita> receitas;

    public void aplicarDesconto(Double desconto) {
        this.preco = this.preco - (this.preco * desconto);
    }

    public void removerDesconto(Double desconto) {
        this.preco = this.preco + (this.preco * desconto);
    }
}

