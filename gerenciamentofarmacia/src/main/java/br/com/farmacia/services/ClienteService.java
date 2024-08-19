package br.com.farmacia.services;

import br.com.farmacia.models.Cliente;
import br.com.farmacia.Repository.ClienteRepository;
import br.com.farmacia.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente criarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente atualizarCliente(Long id, Cliente cliente) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);
        if (clienteExistente.isPresent()) {
            Cliente c = clienteExistente.get();
            c.setNome(cliente.getNome());
            c.setEmail(cliente.getEmail());
            c.setCpf(cliente.getCpf());
            c.setHistorico(cliente.getHistorico());
            return clienteRepository.save(c);
        } else {
            throw new ResourceNotFoundException("Cliente não encontrado com id: " + id);
        }
    }

    public void removerCliente(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Cliente não encontrado com id: " + id);
        }
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }
}
