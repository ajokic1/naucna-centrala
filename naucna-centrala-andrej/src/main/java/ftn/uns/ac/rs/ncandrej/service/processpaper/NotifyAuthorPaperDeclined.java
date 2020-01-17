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
@Named("notifyAuthorPaperDeclined")
public class NotifyAuthorPaperDeclined implements JavaDelegate{
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	MailService mailService;
		
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		User author = userRepo.findByUsername((String) delegateExecution.getVariable("initiator"));
		String journalName = (String) delegateExecution.getVariable("journalName");
		String paperTitle = (String) delegateExecution.getVariable("title");
		mailService.sendMail(author.getEmail(), journalName+" - Paper declined", 
				"Your paper titled \""+paperTitle+"\" has been declined.");
	}
}
