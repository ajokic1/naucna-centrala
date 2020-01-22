package ftn.uns.ac.rs.ncandrej.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ftn.uns.ac.rs.ncandrej.dto.PaperDto;
import ftn.uns.ac.rs.ncandrej.dto.PaperResponseDto;
import ftn.uns.ac.rs.ncandrej.service.PaperService;

@RestController
public class PaperController {
	
	@Autowired
	PaperService paperService;
	
	@GetMapping("/journals/{journalId}/papers")
	public ResponseEntity<List<PaperDto>> getAll(@PathVariable long journalId) {
		return ResponseEntity.ok(paperService.getAll(journalId));
	}
	
	@GetMapping("/papers/{paperId}")
	public ResponseEntity<PaperResponseDto> checkDocument(@PathVariable long paperId, Principal principal) {
		String username = principal.getName();
		return paperService.checkDocument(paperId, username);
	}
	
	@GetMapping("/papers/{paperId}/subscribed")
	public ResponseEntity<Resource> getDocumentSubscribed(@PathVariable long paperId, Principal principal) 
			throws IOException {
		String username = principal.getName();
		return paperService.getDocumentSubscribed(paperId, username);
	}
	
	@GetMapping("/papers/{paperId}/pay")
	public ResponseEntity<String> requestDocumentPayment(@PathVariable long paperId, Principal principal) {
		String username = principal.getName();
		return paperService.requestDocumentPayment(paperId, username);
	}
	
	@GetMapping("/papers/paid/{token}")
	public ResponseEntity<Resource> getDocumentPaid(@PathVariable String token) 
			throws IOException{
		return paperService.getDocumentPaid(token);
	}
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<String> handleIOException() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paper not found.");
	}
	
}
