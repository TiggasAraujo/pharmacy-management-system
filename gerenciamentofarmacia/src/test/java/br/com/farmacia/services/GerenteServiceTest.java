// package br.com.farmacia.services;

// import br.com.farmacia.models.Cliente;
// import br.com.farmacia.models.Gerente;
// import br.com.farmacia.models.Medicamento;
// import br.com.farmacia.models.Promocao;
// import br.com.farmacia.repository.ClienteRepository;
// import br.com.farmacia.repository.GerenteRepository;
// import br.com.farmacia.repository.PromocaoRepository;
// import br.com.farmacia.service.GerenteService;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.ArgumentCaptor;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;

// import java.time.LocalDate;
// import java.util.Arrays;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.*;

// public class GerenteServiceTest {

//     @Mock
//     private GerenteRepository gerenteRepository;

//     @Mock
//     private PromocaoRepository promocaoRepository;

//     @Mock
//     private ClienteRepository clienteRepository;

    
//     private Medicamento medicamento;

//     @Mock
//     private JavaMailSender mailSender;

//     @InjectMocks
//     private GerenteService gerenteService;

//     private Gerente gerente;
//     private Promocao promocao;
//     private List<Cliente> clientes;

//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.openMocks(this);

//         // Criando um gerente de teste
//          gerente = new Gerente();
//         gerente.setId(1L);
//         gerente.setNome("Gerente Teste");

//         Medicamento medicamento = new Medicamento();
//         medicamento.setNome("Paracetamol");
//         medicamento.setPreco(10.00);
//         medicamento.setId(1L);
//         // Criando uma promoção de teste

//          promocao = new Promocao();
//         promocao.setId(1L);
//         promocao.setMedicamento(medicamento);
//         promocao.setDescricao("Promoção de Teste");
//         promocao.setDataInicio(LocalDate.now());
//         promocao.setDataFim(LocalDate.now().plusDays(10));
//         promocao.setDesconto(0.01); // 20% de desconto

//         // Criando uma lista de clientes de teste
//         Cliente cliente1 = new Cliente();
//         cliente1.setNome("Cliente Teste 1");
//         cliente1.setEmail("cliente1@example.com");

//         Cliente cliente2 = new Cliente();
//         cliente2.setNome("Cliente Teste 2");
//         cliente2.setEmail("cliente2@example.com");

//         clientes = Arrays.asList(cliente1, cliente2);
//     }

//     @Test
// public void testEnviarPromocaoParaClientes() {
//     // Configurando mocks
//     when(gerenteRepository.findById(1L)).thenReturn(java.util.Optional.of(gerente));
//     when(promocaoRepository.findById(1L)).thenReturn(java.util.Optional.of(promocao));
//     when(clienteRepository.findAll()).thenReturn(clientes);

//     // Executando o método de teste
//     gerenteService.enviarPromocaoParaClientes(1L, 1L);

//     // Capturando o argumento passado para mailSender.send()
//     ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
//     verify(mailSender, times(2)).send(messageCaptor.capture());

//     // Verificando o conteúdo dos e-mails enviados
//     List<SimpleMailMessage> capturedMessages = messageCaptor.getAllValues();

//     SimpleMailMessage sentMessage1 = capturedMessages.get(0);
//     assertEquals("cliente1@example.com", sentMessage1.getTo()[0]);
//     assertEquals("engenhariadesoftware403@gmail.com", sentMessage1.getFrom());
//     assertEquals("Nova Promoção Disponível!", sentMessage1.getSubject());
//     assertEquals("Olá Cliente Teste 1,\n\n" +
//             "Temos uma nova promoção para você: Promoção de Teste" +
//             "\nDesconto: 1.0% no medicamento: Paracetamol" +
//             "\nVálida de: " + promocao.getDataInicio() + " até " + promocao.getDataFim() +
//             "\n\nAproveite!\n" + gerente.getNome(), sentMessage1.getText());

//     SimpleMailMessage sentMessage2 = capturedMessages.get(1);
//     assertEquals("cliente2@example.com", sentMessage2.getTo()[0]);
//     assertEquals("engenhariadesoftware403@gmail.com", sentMessage2.getFrom());
//     assertEquals("Nova Promoção Disponível!", sentMessage2.getSubject());
//     assertEquals("Olá Cliente Teste 2,\n\n" +
//             "Temos uma nova promoção para você: Promoção de Teste" +
//             "\nDesconto: 1.0% no medicamento: Paracetamol" +
//             "\nVálida de: " + promocao.getDataInicio() + " até " + promocao.getDataFim() +
//             "\n\nAproveite!\n" + gerente.getNome(), sentMessage2.getText());
// }
// }
