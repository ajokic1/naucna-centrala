package ftn.uns.ac.rs.ncandrej.service.createjournal;

import java.util.ArrayList;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

@Service
@Named("createReviewerListHandler")
public class CreateReviewerListHandler implements TaskListener{
	@Override
	public void notify(DelegateTask delegateTask) {
		DelegateExecution delegateExecution = delegateTask.getExecution();
		Long numReviewers = (Long) delegateExecution.getVariable("numReviewers");
		if(numReviewers < 2L) numReviewers=2L;
		delegateExecution.setVariable("numReviewers", numReviewers);
		ArrayList<String> fields = new ArrayList<>();
		delegateExecution.setVariable("reviewers", fields);
	}
}
