package ftn.uns.ac.rs.ncandrej.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.dto.JournalDto;
import ftn.uns.ac.rs.ncandrej.model.Journal;
import ftn.uns.ac.rs.ncandrej.repository.JournalRepository;

@Service
public class JournalService {
	
	@Autowired
	JournalRepository journalRepo;
	
	public boolean checkSubscription(String sellerUuid, String username) throws NotFoundException {
		//Provjera da li je pretplacen na casopis
		return false;
	}
	
	public boolean checkSubscription(long journalId, String username) throws NotFoundException {
		return false;
	}
	
	public List<JournalDto> getAll() {
		List<Journal> journals = journalRepo.findByActiveTrue();
		List<JournalDto> dtos = new ArrayList<>();
		for(Journal journal: journals) {
			dtos.add(new JournalDto(journal));
		}
		return dtos;
	}
	
	public JournalDto get(long journalId) {
		return new JournalDto(journalRepo.findById(journalId).get());
	}
	

}
