package it.prova.agendarest.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.agendarest.dto.AgendaDTO;
import it.prova.agendarest.dto.UtenteDTO;
import it.prova.agendarest.model.Agenda;
import it.prova.agendarest.model.Utente;
import it.prova.agendarest.service.agenda.AgendaService;
import it.prova.agendarest.service.utente.UtenteService;
import it.prova.agendarest.web.api.exception.AgendaNotFoundException;
import it.prova.agendarest.web.api.exception.IdNotNullForInsertException;
import it.prova.agendarest.web.api.exception.UtenteLoggatoNotFoundException;

@RestController
@RequestMapping("/api/agenda")
public class AgendaController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private AgendaService agendaService;

	@GetMapping
	public List<AgendaDTO> getAllby() {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteLoggato = utenteService.findByUsername(username);

		if (utenteLoggato == null) {
			throw new UtenteLoggatoNotFoundException("Nessun utente loggato!");
		}

		return AgendaDTO.createAgendaDTOListFromModelList(agendaService.listAllByUtente(utenteLoggato), true);
	}

	@PostMapping
	public AgendaDTO createNew(@Valid @RequestBody AgendaDTO agendaInput) {
		if (agendaInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteLoggato = utenteService.findByUsername(username);
		
		if (utenteLoggato == null) {
			throw new UtenteLoggatoNotFoundException("Nessun utente loggato!");
		}
		
		agendaInput.setUtenteDTO(UtenteDTO.buildUtenteDTOFromModel(utenteLoggato));

		Agenda agendaInserito = agendaService.inserisciNuovo(agendaInput.buildAgendaModel());
		return AgendaDTO.buildAgendaDTOFromModel(agendaInserito, true);
	}
	
	@GetMapping("/{id}")
	public AgendaDTO findById(@PathVariable(value = "id", required = true) long id) {
		Agenda agenda = agendaService.caricaSingoloElemento(id);

		if (agenda == null)
			throw new AgendaNotFoundException("Agenda not found con id: " + id);

		return AgendaDTO.buildAgendaDTOFromModel(agenda, true);
	}

}
