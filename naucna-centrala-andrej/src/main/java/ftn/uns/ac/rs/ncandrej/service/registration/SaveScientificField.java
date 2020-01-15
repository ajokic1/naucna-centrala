package ftn.uns.ac.rs.ncandrej.service.registration;

import java.util.ArrayList;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class SaveScientificField implements JavaDelegate{
	@Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
		final String field = (String) delegateExecution.getVariable("field");
		ArrayList<String> fields = (ArrayList<String>) delegateExecution.getVariable("fields");
		if (fields==null) fields = new ArrayList<>();
		fields.add(field);
		delegateExecution.setVariable("fields", fields);
	}
}
