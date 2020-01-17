package ftn.uns.ac.rs.ncandrej.service.createjournal;

import java.util.ArrayList;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
@Named("createEditorsListHandler")
public class CreateEditorsListHandler implements JavaDelegate{
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		ArrayList<String> editors = new ArrayList<>();
		delegateExecution.setVariable("editors", editors);
	}
}
