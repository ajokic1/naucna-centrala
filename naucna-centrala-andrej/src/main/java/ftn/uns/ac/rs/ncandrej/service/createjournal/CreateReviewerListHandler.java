package ftn.uns.ac.rs.ncandrej.service.createjournal;

import java.util.ArrayList;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
@Named("createReviewerListHandler")
public class CreateReviewerListHandler implements JavaDelegate{
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		ArrayList<String> fields = new ArrayList<>();
		delegateExecution.setVariable("reviewers", fields);
	}
}
