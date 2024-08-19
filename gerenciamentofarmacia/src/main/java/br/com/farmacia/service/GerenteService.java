package br.com.farmacia.service;

import br.com.farmacia.models.Cliente;
import br.com.farmacia.models.Gerente;
import br.com.farmacia.models.Promocao;
import br.com.farmacia.Repository.ClienteRepository;
import br.com.farmacia.Repository.GerenteRepository;
import br.com.farmacia.Repository.PromocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class GerenteService {

    @Autowired
    private GerenteRepository gerenteRepository;

    @Autowired
    private PromocaoRepository promocaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}") 
    private String remetente;

    public void criarPromocao(Long gerenteId, Promocao promocao) {
        Gerente gerente = gerenteRepository.findById(gerenteId).orElseThrow(() -> new RuntimeException("Gerente não encontrado"));
        promocao.setGerente(gerente);
        promocaoRepository.save(promocao);
    }

    public void enviarPromocaoParaClientes(Long gerenteId, Long promocaoId) {
        Gerente gerente = gerenteRepository.findById(gerenteId).orElseThrow(() -> new IllegalArgumentException("Gerente não encontrado"));
        Promocao promocao = promocaoRepository.findById(promocaoId).orElseThrow(() -> new IllegalArgumentException("Promoção não encontrada"));

        List<Cliente> clientes = clienteRepository.findAll();
        
        for (Cliente cliente : clientes) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(cliente.getEmail());
            message.setFrom("engenhariadesoftware403@gmail.com");
            message.setSubject("Nova Promoção Disponível!");
            message.setText("Olá " + cliente.getNome() + ",\n\n" +
                    "Temos uma nova promoção para você: " + promocao.getDescricao() + 
                    "\nDesconto: " + promocao.getDesconto() * 100+ "% no medicamento: " + promocao.getMedicamento().getNome() + 
                    "\nVálida de: " + promocao.getDataInicio() + " até " + promocao.getDataFim() + 
                    "\n\nAproveite!\n" + gerente.getNome());

            mailSender.send(message);
        }
    }

    public void findById(Long id) {
        gerenteRepository.findById(id);
    }

    public List<Gerente> findAll() {
        return gerenteRepository.findAll();
    }

    public void save(Gerente gerente) {
        gerenteRepository.save(gerente);
    }
}
