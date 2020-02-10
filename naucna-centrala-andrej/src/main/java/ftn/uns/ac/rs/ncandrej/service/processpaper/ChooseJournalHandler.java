package ftn.uns.ac.rs.ncandrej.service.processpaper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import ftn.uns.ac.rs.ncandrej.dto.ListFieldDto;
import ftn.uns.ac.rs.ncandrej.model.Journal;
import ftn.uns.ac.rs.ncandrej.repository.JournalRepository;

@Service
@Named("chooseJournalHandler")
public class ChooseJournalHandler implements TaskListener{
	
	@Autowired
	JournalRepository journalRepo;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		DelegateExecution delegateExecution = delegateTask.getExecution();
		
		// Ucitaj sve casopise i popuni listu za select polje
		List<Journal> journals = journalRepo.findAll();
		Map<String, String> availableJournals = new HashMap<>();
		for(Journal j: journals) {
			availableJournals.put(String.valueOf(j.getId()), j.getName());
		}
		ListFieldDto formField = new ListFieldDto();
		formField.setOptions(availableJournals);
		delegateExecution.setVariable("journalId", formField);
	}

}
