package br.com.farmacia.service;

import br.com.farmacia.models.Vendedor;
import br.com.farmacia.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    public List<Vendedor> listarTodos() {
        return vendedorRepository.findAll();
    }

    public void salvar(Vendedor vendedor) {
        vendedorRepository.save(vendedor);
    }

    public Vendedor buscarPorId(Long id) {
        return vendedorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Vendedor não encontrado"));
    }

    public void excluir(Long id) {
        vendedorRepository.deleteById(id);
    }

    public Object findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public List<Vendedor> buscarTodosVendedores() {
        return vendedorRepository.findAll();
    }
}
