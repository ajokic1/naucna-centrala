package ftn.uns.ac.rs.ncandrej.service.createjournal;

import java.util.ArrayList;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
@Named("inputReviewerHandler")
public class InputReviewerHandler implements JavaDelegate{
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		String editor = (String) delegateExecution.getVariable("editor");
		ArrayList<String> editors = (ArrayList<String>) delegateExecution.getVariable("editors");
		editors.add(editor);
		delegateExecution.setVariable("editors", editors);
	}
}
