package ftn.uns.ac.rs.ncandrej.service.processpaper;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.model.User;
import ftn.uns.ac.rs.ncandrej.repository.UserRepository;
import ftn.uns.ac.rs.ncandrej.util.MailService;

@Service
@Named("notifyFieldEditor")
public class NotifyFieldEditor implements JavaDelegate {
	
	@Autowired
	MailService mailService;
		
	@Autowired
	UserRepository userRepo;
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		User editor = userRepo.findByUsername((String) delegateExecution.getVariable("field_editor"));
		
		String journalName = (String) delegateExecution.getVariable("journalName");
		String paperTitle = (String) delegateExecution.getVariable("title");
				
		mailService.sendMail(editor.getEmail(), journalName+" - New paper submission", 
				"A new paper with the title\"" + paperTitle+"\" has been submitted to your journal.");		
	}
}
