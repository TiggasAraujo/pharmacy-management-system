package br.com.farmacia.services;

import br.com.farmacia.models.Vendedor;
import br.com.farmacia.repository.VendedorRepository;
import br.com.farmacia.service.VendedorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;


public class VendedorServiceTest {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private VendedorService vendedorService;

    @BeforeEach
    public void setup() {
        vendedorService = new VendedorService(vendedorRepository);
    }

    @Test
    public void testSalvarVendedor() {
        Vendedor vendedor = new Vendedor();
        vendedor.setNome("João");

        try {
            vendedorService.salvar(vendedor);

            Vendedor savedVendedor = vendedorRepository.findById(vendedor.getId()).orElse(null);
            Assertions.assertNotNull(savedVendedor);
            Assertions.assertEquals("João", savedVendedor.getNome());
        } catch (Exception ex) {
            System.out.println("Vendedor não existe");
        }
    }

    @Test
    public void testBuscarPorId() {
        Vendedor vendedor = new Vendedor();
        vendedor.setNome("Maria");

        try {
            vendedorRepository.save(vendedor);

            Vendedor foundVendedor = vendedorService.buscarPorId(vendedor.getId());
            Assertions.assertNotNull(foundVendedor);
            Assertions.assertEquals("Maria", foundVendedor.getNome());
        } catch (Exception ex) {
            System.out.println("Vendedor não existe");
        }
    }

    @Test
    public void testSalvarVendedorVerificaDados() {
        Vendedor vendedor = new Vendedor();
        vendedor.setNome("Fernanda");
        vendedor.setCpf("12345678900");


        try {
            vendedorRepository.save(vendedor);

            Vendedor savedVendedor = vendedorRepository.findById(vendedor.getId()).orElse(null);

            Assertions.assertNotNull(savedVendedor);
            Assertions.assertEquals("Fernanda", savedVendedor.getNome());
            Assertions.assertEquals("12345678900", savedVendedor.getCpf());
        }  catch (Exception ex) {
            System.out.println("Vendedor não existe");
        }

    }


    @Test
    public void testExcluirVendedor() {
        Vendedor vendedor = new Vendedor();
        vendedor.setNome("Pedro");

        try {

            vendedorRepository.save(vendedor);

            vendedorService.excluir(vendedor.getId());

            Assertions.assertFalse(vendedorRepository.existsById(vendedor.getId()));
        } catch (Exception ex) {
            System.out.println("Vendedor não existe");
        }
    }

}

