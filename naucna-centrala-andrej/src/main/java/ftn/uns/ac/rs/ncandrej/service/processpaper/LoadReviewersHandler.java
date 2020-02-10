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
import ftn.uns.ac.rs.ncandrej.model.Reviewer;
import ftn.uns.ac.rs.ncandrej.model.ScientificField;
import ftn.uns.ac.rs.ncandrej.repository.ReviewerRepository;

@Service
@Named("loadReviewersHandler")
public class LoadReviewersHandler implements TaskListener{
	
	@Autowired
	ReviewerRepository reviewerRepo;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		DelegateExecution delegateExecution = delegateTask.getExecution();
		List<Reviewer> availableFields = reviewerRepo.findAll();
		Map<String, String> availableFieldsMap = new HashMap<>();
		for(Reviewer f: availableFields) {
			availableFieldsMap.put(f.getUsername(), f.getFirstName() + " " + f.getLastName());
		}
		ListFieldDto formField = new ListFieldDto();
		formField.setOptions(availableFieldsMap);
		delegateExecution.setVariable("reviewer", formField);
		
	}

}
