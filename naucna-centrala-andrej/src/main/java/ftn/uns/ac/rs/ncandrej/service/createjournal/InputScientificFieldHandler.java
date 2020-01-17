package ftn.uns.ac.rs.ncandrej.service.createjournal;

import java.util.ArrayList;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
@Named("inputScientificFieldHandler")
public class InputScientificFieldHandler implements JavaDelegate {
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		String field = (String) delegateExecution.getVariable("field");
		ArrayList<String> fields = (ArrayList<String>) delegateExecution.getVariable("fields");
		fields.add(field);
		delegateExecution.setVariable("fields", fields);
	}
}
