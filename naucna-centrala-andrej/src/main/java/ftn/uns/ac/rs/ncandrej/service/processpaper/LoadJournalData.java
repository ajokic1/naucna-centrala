package ftn.uns.ac.rs.ncandrej.service.processpaper;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.model.Journal;
import ftn.uns.ac.rs.ncandrej.repository.JournalRepository;

@Service
@Named("loadJournalData")
public class LoadJournalData implements JavaDelegate{
	
	@Autowired
	JournalRepository journalRepo;
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		Journal journal = journalRepo.findById((Long) delegateExecution.getVariable("journalId")).get();
		delegateExecution.setVariable("openAccess", journal.isOpenAccess());
		delegateExecution.setVariable("journalName", journal.getName());
		delegateExecution.setVariable("journalFields", journal.getFields());
		delegateExecution.setVariable("issn", journal.getIssn());
		delegateExecution.setVariable("fieldEditors", journal.getFieldEditors());
		delegateExecution.setVariable("reviewers", journal.getReviewers());
		
	}
}
