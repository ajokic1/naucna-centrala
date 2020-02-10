package ftn.uns.ac.rs.ncandrej.service.createjournal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.dto.ListFieldDto;
import ftn.uns.ac.rs.ncandrej.model.ScientificField;
import ftn.uns.ac.rs.ncandrej.repository.ScientificFieldRepository;

@Service
@Named("createFieldListHandler")
public class CreateFieldListHandler implements TaskListener{
	
	@Autowired
	ScientificFieldRepository fieldRepo;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		DelegateExecution delegateExecution = delegateTask.getExecution();
		
		// Load scientific fields from database and populate list
		List<ScientificField> availableFields = fieldRepo.findAll();
		Map<String, String> availableFieldsMap = new HashMap<>();
		for(ScientificField f: availableFields) {
			availableFieldsMap.put(f.getCode(), f.getName());
		}
		ListFieldDto formField = new ListFieldDto();
		formField.setOptions(availableFieldsMap);
		delegateExecution.setVariable("field", formField);	
		
		ArrayList<String> selectedFields = new ArrayList<>();
		delegateExecution.setVariable("fields", selectedFields);
	}
}
