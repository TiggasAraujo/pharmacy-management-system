package br.com.farmacia.service;

import br.com.farmacia.models.Vendedor;
import br.com.farmacia.repository.VendedorRepository;
import br.com.farmacia.exceptions.VendedorNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendedorService {

    private final VendedorRepository vendedorRepository;

    // Construtor que recebe VendedorRepository
    public VendedorService(VendedorRepository vendedorRepository) {
        this.vendedorRepository = vendedorRepository;
    }

    public void salvar(Vendedor vendedor) {
        vendedorRepository.save(vendedor);
    }

    public Vendedor buscarPorId(Long id) {
        return vendedorRepository.findById(id)
                .orElseThrow(() -> new VendedorNotFoundException("Vendedor não encontrado com id: " + id));
    }

    public void excluir(Long id) {
        if (!vendedorRepository.existsById(id)) {
            throw new VendedorNotFoundException("Vendedor não encontrado com id: " + id);
        }
        vendedorRepository.deleteById(id);
    }

    public List<Vendedor> buscarTodosVendedores() {
        return vendedorRepository.findAll();
    }
}