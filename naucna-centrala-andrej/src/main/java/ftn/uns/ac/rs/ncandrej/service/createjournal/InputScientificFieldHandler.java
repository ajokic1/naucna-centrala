package ftn.uns.ac.rs.ncandrej.service.createjournal;

import java.util.ArrayList;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

@Service
@Named("inputScientificFieldHandler")
public class InputScientificFieldHandler implements TaskListener {
	@Override
	public void notify(DelegateTask delegateTask) {
		DelegateExecution delegateExecution = delegateTask.getExecution();
		String field = (String) delegateExecution.getVariable("field");
		ArrayList<String> fields = (ArrayList<String>) delegateExecution.getVariable("fields");
		fields.add(field);
		delegateExecution.setVariable("fields", fields);
	}
}
