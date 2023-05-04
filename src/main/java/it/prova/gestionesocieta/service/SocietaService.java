package it.prova.gestionesocieta.service;

import java.time.LocalDate;
import java.util.List;

import it.prova.gestionesocieta.model.Societa;

public interface SocietaService {
	public void inserisciNuovo(Societa societaInstance);
	public List<Societa> findByExample(Societa societaExample);
	public void rimuoviSocieta(Long idSocieta) throws Exception;
	public List<Societa> trovaConDipendetiConRedditoAnnuoMaggioreDi (int redditoAnno);
    public List<Societa> trovaSocietaFondatePrimaDi(LocalDate dataFondazione);

}
