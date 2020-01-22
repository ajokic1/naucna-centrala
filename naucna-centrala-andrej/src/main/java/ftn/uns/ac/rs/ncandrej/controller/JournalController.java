package ftn.uns.ac.rs.ncandrej.controller;

import java.security.Principal;
import java.util.List;

import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.uns.ac.rs.ncandrej.dto.JournalDto;
import ftn.uns.ac.rs.ncandrej.service.JournalService;

@RestController
@RequestMapping("/journals")
public class JournalController {
	
	@Autowired
	JournalService journalService;
	
	@GetMapping
	public ResponseEntity<List<JournalDto>> getAll() {
		return ResponseEntity.ok(journalService.getAll());
	}
	
	@GetMapping("/{journalId}")
	public ResponseEntity<JournalDto> get(@PathVariable long journalId) {
		return ResponseEntity.ok(journalService.get(journalId));		
	}
	
	@GetMapping("/{journalId}/subscribe")
	public ResponseEntity<String> subscribe(@PathVariable long journalId) {
		return ResponseEntity.ok("");
	}
	
	@GetMapping("/{journalId}/subscribe/check")
	public ResponseEntity<Boolean> checkSubscription(@PathVariable long journalId, Principal principal) 
			throws NotFoundException {
		return ResponseEntity.ok(journalService.checkSubscription(journalId, principal.getName()));
	}
}
