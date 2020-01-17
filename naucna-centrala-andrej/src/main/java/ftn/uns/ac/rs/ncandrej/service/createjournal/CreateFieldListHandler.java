package ftn.uns.ac.rs.ncandrej.service.createjournal;

import java.util.ArrayList;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

@Service
@Named("createFieldListHandler")
public class CreateFieldListHandler implements TaskListener{
	@Override
	public void notify(DelegateTask delegateTask) {
		DelegateExecution delegateExecution = delegateTask.getExecution();
		ArrayList<String> fields = new ArrayList<>();
		delegateExecution.setVariable("fields", fields);
	}
}
