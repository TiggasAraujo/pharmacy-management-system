package br.com.farmacia.service;

import br.com.farmacia.models.Venda;
import br.com.farmacia.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    public Venda salvarVenda(Venda venda) {
        BigDecimal valorTotal = venda.getItens().stream()
                .map(item -> item.getPrecoPorItem().multiply(new BigDecimal(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
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
}
