package ftn.uns.ac.rs.ncandrej.service.processpaper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
@Named("chooseReviewerHandler")
public class ChooseReviewerHandler implements JavaDelegate {
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		List<String> reviewers = (ArrayList<String>)delegateExecution.getVariable("selectedReviewers");
		reviewers.add((String)delegateExecution.getVariable("reviewer"));
		delegateExecution.setVariable("selectedReviewers", reviewers);
		
	}
}
