package it.prova.gestionesocieta.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.model.Societa;
import it.prova.gestionesocieta.repository.DipendenteRepository;

@Service
public class DipendenteServiceImpl implements DipendenteService {
	@Autowired
	private DipendenteRepository dipendenteRepository;
	
	@PersistenceContext
	private EntityManager entityManager;


	@Transactional
	public void inserisciNuovo(Dipendente dipendenteInstance) {
		dipendenteRepository.save(dipendenteInstance);
	}


	@Override
	public List<Dipendente> cercaPerSocieta(Societa societaInput) {
		return dipendenteRepository.findBySocieta(societaInput);
	}

	
	@Transactional(readOnly = true)
	public List<Dipendente> listaDipendenti() {
		return (List<Dipendente>) dipendenteRepository.findAll();
		
		
		
	}


	@Override
	public void modificaDipendente(Dipendente dipendenteInstance) {
		dipendenteRepository.save(dipendenteInstance);
	}

}
