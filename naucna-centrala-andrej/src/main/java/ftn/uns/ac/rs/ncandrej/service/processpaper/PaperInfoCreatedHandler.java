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
import ftn.uns.ac.rs.ncandrej.model.ScientificField;
import ftn.uns.ac.rs.ncandrej.repository.JournalRepository;

@Service
@Named("paperInfoCreatedHandler")
public class PaperInfoCreatedHandler implements TaskListener{
	
	@Autowired
	JournalRepository journalRepo;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		DelegateExecution delegateExecution = delegateTask.getExecution();
		String journalId = (String) delegateExecution.getVariable("journalId");
		Journal journal = journalRepo.findById(Long.parseLong(journalId)).get();
		
		List<ScientificField> availableFields = journal.getFields();
		Map<String, String> availableFieldsMap = new HashMap<>();
		for(ScientificField f: availableFields) {
			availableFieldsMap.put(f.getCode(), f.getName());
		}
		ListFieldDto formField = new ListFieldDto();
		formField.setOptions(availableFieldsMap);
		delegateExecution.setVariable("field", formField);		
	}

}
