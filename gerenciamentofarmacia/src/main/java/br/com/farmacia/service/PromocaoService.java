package br.com.farmacia.service;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.farmacia.models.Medicamento;
import br.com.farmacia.models.Promocao;
import br.com.farmacia.repository.MedicamentoRepository;
import br.com.farmacia.repository.PromocaoRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PromocaoService {

    @Autowired
    private PromocaoRepository promocaoRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    public void save(Promocao promocao) {
        promocaoRepository.save(promocao);
        aplicarPromocao(promocao.getId());
    }

    public void aplicarPromocao(Long promocaoId) {
        Optional<Promocao> promocaoOpt = promocaoRepository.findById(promocaoId);
        if (promocaoOpt.isPresent()) {
            Promocao promocao = promocaoOpt.get();
            Medicamento medicamento = promocao.getMedicamento();
            
            // Calcula o novo preço após aplicar o desconto
            double desconto = promocao.getDesconto();
            double precoOriginal = medicamento.getPreco();
            double precoComDesconto = precoOriginal * (1 - desconto);
            
            medicamento.setPreco(precoComDesconto);
            
            // Atualiza o medicamento no repositório
            medicamentoRepository.save(medicamento);
        }
    } 

    public void removerPromocao(Long promocaoId) {
        Promocao promocao = promocaoRepository.findById(promocaoId)
                .orElseThrow(() -> new IllegalArgumentException("Promoção não encontrada"));
    
        Medicamento medicamento = promocao.getMedicamento();
        if (medicamento != null) {
            Double precoOriginal = calcularPrecoOriginal(medicamento.getPreco(), promocao.getDesconto());
            medicamento.setPreco(precoOriginal);
            medicamentoRepository.save(medicamento);
        }
        
        promocaoRepository.deleteById(promocaoId);
    }
    

    public Double calcularPrecoOriginal(Double precoAtual, Double desconto) {
        BigDecimal precoAtualBD = BigDecimal.valueOf(precoAtual);
        BigDecimal descontoBD = BigDecimal.valueOf(desconto);
        BigDecimal precoOriginalBD = precoAtualBD.divide(BigDecimal.ONE.subtract(descontoBD), 2, RoundingMode.HALF_UP);
        return precoOriginalBD.doubleValue();
    }
    

    // Deleta uma promoção
    public void deleteById(Long id) {
        promocaoRepository.deleteById(id);
    }

    // Encontra uma promoção pelo ID
    public Promocao getById(Long id) {
        return promocaoRepository.findById(id).orElse(null);
    }

    // Retorna todas as promoções
    public List<Promocao> findAll() {
        return promocaoRepository.findAll();
    }
}