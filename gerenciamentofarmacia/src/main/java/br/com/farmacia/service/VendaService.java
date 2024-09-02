package br.com.farmacia.service;

import br.com.farmacia.models.Cliente;
import br.com.farmacia.models.Medicamento;
import br.com.farmacia.models.Venda;
import br.com.farmacia.models.Vendedor;
import br.com.farmacia.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private MedicamentoService medicamentoService;


    @Transactional
public Venda salvarVenda(Venda venda) {
    double valorTotal = venda.getItens().stream()
            .mapToDouble(item -> {
                Medicamento medicamento = medicamentoService.buscarPorId(item.getMedicamento().getId())
                        .orElseThrow(() -> new RuntimeException("Medicamento não encontrado"));
                double preco = medicamento.getPreco();
                return preco * item.getQuantidade();
            })
            .sum();

    venda.setValorTotal(valorTotal);
    return vendaRepository.save(venda);
}



    public Venda buscarPorId(Long id) {
        return vendaRepository.findById(id).orElseThrow(() -> new RuntimeException("Venda não encontrada"));
    }

    public void excluirVenda(Long id) {
        vendaRepository.deleteById(id);
    }

    public List<Venda> buscarTodasVendas() {
        return vendaRepository.findAll();
    }

    // Novo método para buscar todas as vendas de um cliente
    public List<Venda> buscarVendasPorCliente(Cliente cliente) {
        return vendaRepository.findByCliente(cliente);
    }

    // Novo método para buscar todas as vendas de um vendedor
    public List<Venda> buscarVendasPorVendedor(Vendedor vendedor) {
        return vendaRepository.findByVendedor(vendedor);
    }
}
