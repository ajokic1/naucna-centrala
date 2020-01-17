package ftn.uns.ac.rs.ncandrej.service.processpaper;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.uns.ac.rs.ncandrej.model.Editor;
import ftn.uns.ac.rs.ncandrej.repository.EditorRepository;
import ftn.uns.ac.rs.ncandrej.util.MailService;

@Service
@Named("notifyReviewExpired")
public class NotifyReviewExpired implements JavaDelegate{
	
	@Autowired
	EditorRepository editorRepo;
	
	@Autowired
	MailService mailService;
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		Editor editor = editorRepo.findByUsername((String)delegateExecution.getVariable("fieldEditor"));
		mailService.sendMail(editor.getEmail(), "Review expired", "The chosen reviewer did not complete the review in time. Please choose another reviewer.");
	}
}
