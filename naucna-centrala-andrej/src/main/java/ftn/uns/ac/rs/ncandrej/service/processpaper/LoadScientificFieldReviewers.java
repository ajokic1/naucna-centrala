package ftn.uns.ac.rs.ncandrej.service.processpaper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.model.Reviewer;
import ftn.uns.ac.rs.ncandrej.model.ScientificField;
import ftn.uns.ac.rs.ncandrej.repository.ScientificFieldRepository;

@Service
@Named("loadScientificFieldReviewers")
public class LoadScientificFieldReviewers implements JavaDelegate {
	
	@Autowired
	ScientificFieldRepository fieldRepo;
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		List<Reviewer> reviewers = (ArrayList<Reviewer>) delegateExecution.getVariable("reviewers");
		ScientificField field = fieldRepo.findByCode((String) delegateExecution.getVariable("field"));
		List<String> fieldReviewers = new ArrayList<>();
		for(Reviewer reviewer: reviewers) {
			if(reviewer.getFields().contains(field))
				fieldReviewers.add(reviewer.getUsername());
		}
		delegateExecution.setVariable("selectedReviewers", new ArrayList<String>());
		delegateExecution.setVariable("fieldReviewers", fieldReviewers);
		delegateExecution.setVariable("hasReviewers", !fieldReviewers.isEmpty());
	}
}
