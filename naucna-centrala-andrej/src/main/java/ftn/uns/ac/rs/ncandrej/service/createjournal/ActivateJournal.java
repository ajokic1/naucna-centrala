package ftn.uns.ac.rs.ncandrej.service.createjournal;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.model.Journal;
import ftn.uns.ac.rs.ncandrej.repository.EditorRepository;
import ftn.uns.ac.rs.ncandrej.repository.JournalRepository;
import ftn.uns.ac.rs.ncandrej.repository.ReviewerRepository;

@Service
@Named("activateJournal")
public class ActivateJournal implements JavaDelegate{
	
	@Autowired
	JournalRepository journalRepo;
	
	@Autowired
	ReviewerRepository reviewerRepo;
	
	@Autowired
	EditorRepository editorRepo;
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		Journal journal = journalRepo.findByIssn((String)delegateExecution.getVariable("issn"));
		journal.setActive(true);
		journalRepo.save(journal);
	}
}
