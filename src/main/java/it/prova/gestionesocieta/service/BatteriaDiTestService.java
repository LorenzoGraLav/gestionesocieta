package it.prova.gestionesocieta.service;

import java.time.LocalDate;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.model.Societa;

@Service
public class BatteriaDiTestService {

	@Autowired
	private SocietaService societaService;

	@Autowired
	private DipendenteService dipendenteService;

	public void testInserisciNuovaSocieta() {
		Long nowInMillisecondi = new Date().getTime();

		LocalDate dataFondazione = LocalDate.parse("2005-05-10");

		Societa nuovaSocieta = new Societa("Societa" + nowInMillisecondi, "Via dei" + nowInMillisecondi,
				dataFondazione);
		if (nuovaSocieta.getId() != null)
			throw new RuntimeException("testInserisciNuovaSocieta...failed: transient object con id valorizzato");
		// salvo
		societaService.inserisciNuovo(nuovaSocieta);
		if (nuovaSocieta.getId() == null || nuovaSocieta.getId() < 1)
			throw new RuntimeException("testInserisciNuovaSocieta...failed: inserimento fallito");

		System.out.println(nuovaSocieta);

		System.out.println("testInserisciNuovaSocieta........OK");
	}

	public void testInserisciNuovoDipendente() {

		System.out.println("............. testInserisciNuovoDipendente: inizio.................");

		Societa samsung = new Societa("samsung", "via dei draghi 4", LocalDate.of(1992, 9, 10));
		societaService.inserisciNuovo(samsung);
		if (samsung.getId() == null) {
			throw new RuntimeException("errore: societa non inserita.");
		}
		Dipendente dipendente = new Dipendente("Tizio", "Caio", LocalDate.of(2012, 11, 2), 30000, samsung);

		dipendenteService.inserisciNuovo(dipendente);
		dipendente.setSocieta(samsung);
		if (dipendente.getId() == null) {
			throw new RuntimeException("errore: dipendente non inserito.");

		}
		System.out.println(dipendente);
		System.out.println(".................. testInserisciNuovoDipendente: fine PASSED..............");

	}

	public void testFindByExampleSocieta() {

		LocalDate dataFondazione = LocalDate.parse("2005-05-10");

		Societa societa = new Societa("Societa2", "Via dei navigatori", dataFondazione);
		societaService.inserisciNuovo(societa);
		Societa societaDaCercare = new Societa();
		List<Societa> listasocieta = societaService.findByExample(societaDaCercare);

		System.out.println(listasocieta);
	}

	public void testAggiornaDipendente() {

		System.out.println(".................... testAggiornaDipendente inizio ...............");

		Societa asus = new Societa("Asus", "via delle montagne 43", LocalDate.of(1995, 2, 13));
		societaService.inserisciNuovo(asus);
		if (asus.getId() == null) {
			throw new RuntimeException("errore societa non inserita correttamente");
		}
		Dipendente dipendenteDaInserire = new Dipendente("Carlo", "Boeri", LocalDate.of(2013, 10, 2), 20000, asus);

		dipendenteService.inserisciNuovo(dipendenteDaInserire);
		;
		dipendenteDaInserire.setSocieta(asus);
		String nomeAggiornato = "Lorenzo";
		dipendenteDaInserire.setNome(nomeAggiornato);
		dipendenteService.modificaDipendente(dipendenteDaInserire);
		System.out.println(dipendenteDaInserire);
		System.out.println(".................... testAggiornaDipendente: fine PASSED ...............");

	}

	public void testTrovaIDipendentiConRedditoAnnuoMaggioreDi() {
		System.out.println(
				"...................... testTrovaIDipendentiConRedditoAnnuoMaggioreDi inizio.................");
		int redditoEsempio = 10000;
		Societa gamestop = new Societa("gamestop", "via degli astri 89", LocalDate.of(1995, 9, 10));
		societaService.inserisciNuovo(gamestop);

		if (gamestop.getId() == null) {
			throw new RuntimeException("errore nessuna società trovata.");
		}
		Societa primigi = new Societa("primigi", "via delle acacie 51", LocalDate.of(1998, 9, 10));
		societaService.inserisciNuovo(primigi);
		if (primigi.getId() == null) {
			throw new RuntimeException("errore non riesco a trovare la società richiesta.");
		}
		Dipendente primoDipendente = new Dipendente("Mauro", "Casciani", LocalDate.of(2010, 9, 2), 24000, gamestop);
		dipendenteService.inserisciNuovo(primoDipendente);
		Dipendente secondoDipendente = new Dipendente("Marta", "Ciolli", LocalDate.of(2014, 8, 10), 230000, primigi);
		dipendenteService.inserisciNuovo(secondoDipendente);

		List<Societa> listaSocietaDipendentiConRedditoAnnuoMaggioreDi = societaService
				.trovaConDipendetiConRedditoAnnuoMaggioreDi(redditoEsempio);

		System.out.println(listaSocietaDipendentiConRedditoAnnuoMaggioreDi);
		System.out.println(".............. testTrovaIDipendentiConRedditoAnnuoMaggioreDi: fine PASSED.............");

	}

	public Dipendente testTrovaDipendentePiuAnziano() {
		System.out.println("..............TestTrovaDipendentePiuAnziano inizio...........");
		LocalDate dataFondazione = LocalDate.of(1988, 2, 1); // data di fondazione delle società da considerare

		List<Societa> societaFondatePrima1990 = societaService.trovaSocietaFondatePrimaDi(dataFondazione);
		;
		// trova tutte le società fondate prima del 1990

		Dipendente dipendentePiuAnziano = new Dipendente("Marco", "Paladri", LocalDate.of(1990, 3, 13), 20000);
		LocalDate dataAssunzioneDipendentePiuAnziano = LocalDate.parse("1990-02-12");

		for (Societa societa : societaFondatePrima1990) {
			for (Dipendente dipendente : societa.getDipendenti()) {
				LocalDate dataAssunzione = dipendente.getDataAssunzione();

				if (dipendentePiuAnziano == null || dataAssunzione.isBefore(dataAssunzioneDipendentePiuAnziano)) {
					// se questo dipendente è più anziano del dipendente più anziano finora trovato,
					// sostituiscilo
					dipendentePiuAnziano = dipendente;
					dataAssunzioneDipendentePiuAnziano = dataAssunzione;
				}
			}
		}
		System.out.println(dipendentePiuAnziano);

		System.out.println("TestTrovaDipendentePiuAnziano fine: PASSED...................");
		return dipendentePiuAnziano;

	}

}
