package ftn.uns.ac.rs.ncandrej.service.processpaper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;


@Service
@Named("setMainEditorAsReviewer")
public class SetMainEditorAsReviewer implements JavaDelegate {
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		List<String> reviewers = new ArrayList<>();
		reviewers.add((String)delegateExecution.getVariable("mainEditor"));
		delegateExecution.setVariable("selectedReviewers", reviewers);
		
	}
}
