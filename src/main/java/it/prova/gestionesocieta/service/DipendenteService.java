package it.prova.gestionesocieta.service;

import java.util.List;

import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.model.Societa;

public interface DipendenteService {
	public void inserisciNuovo(Dipendente dipendenteInstance);
	
	public void modificaDipendente (Dipendente dipendenteInstance);

	public List<Dipendente> cercaPerSocieta(Societa societaInput);

	public List<Dipendente> listaDipendenti();

}
