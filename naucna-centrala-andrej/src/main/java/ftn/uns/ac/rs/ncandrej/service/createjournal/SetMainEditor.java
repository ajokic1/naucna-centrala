package ftn.uns.ac.rs.ncandrej.service.createjournal;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.model.Editor;
import ftn.uns.ac.rs.ncandrej.model.Journal;
import ftn.uns.ac.rs.ncandrej.repository.EditorRepository;
import ftn.uns.ac.rs.ncandrej.repository.JournalRepository;

@Service
@Named("setMainEditor")
public class SetMainEditor implements JavaDelegate {
	
	@Autowired
	JournalRepository journalRepo;
	
	@Autowired
	EditorRepository editorRepo;
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		Journal journal = journalRepo.findByIssn((String) delegateExecution.getVariable("issn"));
		Editor mainEditor = editorRepo.findByUsername((String) delegateExecution.getVariable("initiator"));
		journal.setMainEditor(mainEditor);
	}
}
