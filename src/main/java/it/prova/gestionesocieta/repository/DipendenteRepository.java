package it.prova.gestionesocieta.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.model.Societa;



public interface DipendenteRepository extends CrudRepository<Dipendente, Long>,QueryByExampleExecutor <Dipendente>  {

	List<Dipendente> findBySocieta(Societa societaInput);
//	@EntityGraph(attributePaths = {"societa"})
//	Dipendente findByMaxEtaAndSocieta_DataFondazioneSmallerThan (LocalDate dataFondazioneSocieta);
}
