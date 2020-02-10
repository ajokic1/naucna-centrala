package ftn.uns.ac.rs.ncandrej.service.processpaper;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.model.User;
import ftn.uns.ac.rs.ncandrej.repository.UserRepository;
import ftn.uns.ac.rs.ncandrej.util.MailService;
import lombok.extern.slf4j.Slf4j;

@Service
@Named("notifyEditorsPaperSubmission")
@Slf4j
public class NotifyEditorsPaperSubmission implements JavaDelegate{
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	MailService mailService;
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		User mainEditor = userRepo.findByUsername((String) delegateExecution.getVariable("mainEditor"));
		User author = userRepo.findByUsername((String) delegateExecution.getVariable("initiator"));
		
		String journalName = (String) delegateExecution.getVariable("journalName");
		String paperTitle = (String) delegateExecution.getVariable("title");
		
		mailService.sendMail(mainEditor.getEmail(), journalName+" - New paper submission", 
				"A new paper with the title\"" + paperTitle+"\" has been submitted to your journal.");
		mailService.sendMail(author.getEmail(), journalName+" - Paper submitted", 
				"You have successfully submitted a new paper. Waiting for editor revision.");
	}
}
