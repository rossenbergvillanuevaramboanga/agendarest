package it.prova.agendarest.service.agenda;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.prova.agendarest.model.Agenda;
import it.prova.agendarest.repository.agenda.AgendaRepository;

public class AgendaServiceImpl implements AgendaService {
	
	@Autowired
	private AgendaRepository agendaRepository;

	@Override
	public List<Agenda> listAll() {
		// TODO Auto-generated method stub
		return (List<Agenda>) agendaRepository.findAll();
	}

	@Override
	public Agenda caricaSingoloElemento(Long id) {
		// TODO Auto-generated method stub
		return agendaRepository.findById(id).orElse(null);
	}

	@Override
	public void aggiorna(Agenda agendaInstance) {
		// TODO Auto-generated method stub
		agendaRepository.save(agendaInstance);
		
	}

	@Override
	public void inserisciNuovo(Agenda agendaInstance) {
		// TODO Auto-generated method stub
		agendaRepository.save(agendaInstance);
		
	}

	@Override
	public void rimuovi(Long idToRemove) {
		// TODO Auto-generated method stub
		agendaRepository.deleteById(idToRemove);
	}

}
