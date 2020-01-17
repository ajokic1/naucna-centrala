package ftn.uns.ac.rs.ncandrej.service.createjournal;

import java.util.ArrayList;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.model.Journal;
import ftn.uns.ac.rs.ncandrej.model.ScientificField;
import ftn.uns.ac.rs.ncandrej.repository.JournalRepository;
import ftn.uns.ac.rs.ncandrej.repository.ScientificFieldRepository;

@Service
@Named("saveJournalData")
public class SaveJournalData implements JavaDelegate {
	
	@Autowired
	JournalRepository journalRepo;
	
	@Autowired
	ScientificFieldRepository fieldRepo;
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		String name = (String) delegateExecution.getVariable("name");
		String issn = (String) delegateExecution.getVariable("issn");
		Boolean openAccess = (Boolean) delegateExecution.getVariable("openAccess");
		
		ArrayList<String> fields = (ArrayList<String>) delegateExecution.getVariable("fields");
		ArrayList<ScientificField> scFields = new ArrayList<>();
		for(String field: fields) {
			scFields.add(fieldRepo.findByCode(field));
		}
		
		Journal journal = new Journal();
		journal.setName(name);
		journal.setIssn(issn);
		journal.setOpenAccess(openAccess);
		journal.setFields(scFields);
		
		journalRepo.save(journal);		
	}	
}
