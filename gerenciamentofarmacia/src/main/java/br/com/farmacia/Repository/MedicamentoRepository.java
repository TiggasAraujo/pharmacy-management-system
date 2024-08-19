package br.com.farmacia.repository;

import br.com.farmacia.models.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
	// @Query("SELECT a FROM Medicamento a WHERE a.qtdEstoque > 0")
	// List<Medicamento> findByQtdEstoque();

	// @Query("SELECT i FROM Medicamento i WHERE i.qtdEstoque = 0")
	// List<Medicamento> findSemEstoque(); 
	
	// /*medicamento necessita do atributo vencimento*/
	// @Query("SELECT b FROM Medicamento b WHERE b.vencimento = False ")
	// List<Medicamento> findByVencimento();

	List<Medicamento> findByNomeContainingIgnoreCase(String nome);
}
