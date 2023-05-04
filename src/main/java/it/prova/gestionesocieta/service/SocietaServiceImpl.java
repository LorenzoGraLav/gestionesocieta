package it.prova.gestionesocieta.service;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.model.Societa;
import it.prova.gestionesocieta.repository.DipendenteRepository;
import it.prova.gestionesocieta.repository.SocietaRepository;

@Service
public class SocietaServiceImpl implements SocietaService {

	@Autowired
	private SocietaRepository societaRepository;

	@Autowired
	private DipendenteRepository dipendenteRepository;

	// questo mi serve per il findByExample2 che risulta 'a mano'
	// o comunque in tutti quei casi in cui ho bisogno di costruire custom query nel
	// service
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void inserisciNuovo(Societa societaInstance) {
		societaRepository.save(societaInstance);
	}

	@Override
	public List<Societa> findByExample(Societa societaExample) {
		String query = "select s from Societa s where s.id = s.id ";

		if (StringUtils.isNotEmpty(societaExample.getRagioneSociale()))
			query += " and s.ragioneSociale like '%" + societaExample.getRagioneSociale() + "%' ";
		if (StringUtils.isNotEmpty(societaExample.getIndirizzo()))
			query += " and s.indirizzo like '%" + societaExample.getIndirizzo() + "%' ";
		if (societaExample.getDataFondazione() != null)
			query += " and s.dataFondazione = " + societaExample.getDataFondazione();

		return entityManager.createQuery(query, Societa.class).getResultList();
	}

	@Transactional
	public void rimuoviSocieta(Long idSocieta) throws Exception {

		Societa societa = societaRepository.findById(idSocieta).orElseThrow(() -> new Exception("Società non trovata"));

		List<Dipendente> dipendenti = dipendenteRepository.findBySocieta(societa);

		if (!dipendenti.isEmpty()) {
			throw new Exception("Impossibile rimuovere la Società perché ha Dipendenti");
		}

		societaRepository.delete(societa);

	}

	@Override
	public List<Societa> trovaConDipendetiConRedditoAnnuoMaggioreDi(int redditoAnno) {
		return societaRepository.findAllDistinctByDipendenti_RedditoAnnuoLordoGreaterThan(redditoAnno);
	}

	@Override
	public List<Societa> trovaSocietaFondatePrimaDi(LocalDate dataFondazione) {
		return societaRepository.findByDataFondazioneBefore(dataFondazione);
	}
	
	
	

}
